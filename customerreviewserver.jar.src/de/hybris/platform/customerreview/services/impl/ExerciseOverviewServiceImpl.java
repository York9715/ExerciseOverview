package de.hybris.platform.customerreview.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.customerreview.exception.*;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.customerreview.services;

@Service
@PropertySource("classpath:/application.properties")
public class ExerciseOverviewServiceImpl implements ExerciseOverviewService  {

	private static final Logger log = Logger.getLogger(ExerciseOverview.class);

	@Value("#{'${cursewords.list}'.split(';')}")
	private List<String> curseWords;

	@Autowired
	CustomerReviewService customerReviewService;

	public ExerciseOverviewServiceImpl() {
	}

	public int getNumberOfReviews(ProductModel paramProductModel,
			Double ratingFrom, Double ratingTo) {

		int ret = 0;

		try {
			List<CustomerReviewModel> reviews = customerReviewService
					.getAllReviews(paramProductModel);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

		for (CustomerReviewModel review : reviews) {
			if (review.getRating() >= ratingFrom
					&& review.getRating() <= ratingTo)
				ret++;
		}

		log.debug("Total " + ret + " reviews between " + ratingFrom + " and "
				+ ratingTo + " for product " + paramProductModel.toString());

		return ret;
	}

	@Async
	public CustomerReviewModel createCustomerReview(Double rating,
			String comment, UserModel paramUserModel,
			ProductModel paramProductMode) throws FoundCurseWordsExcetion,
			InCorrectRatingExcetion {

		if (rating < CustomerReviewConstants.DEFAULTS.MINIMAL_RATING) {
			log.debug("Rating " + rating + " <"
					+ CustomerReviewConstants.DEFAULTS.MINIMAL_RATING);
			throw new InCorrectRatingExcetion();
		}

		ArrayList<String> wkCurseWords = new ArrayList();

		for (String word : this.curseWords) {
			if (Pattern.matches(word, comment))
				wkCurseWords.add(word);
		}

		if ( wkCurseWords.size() > 0 ) {
			throw new InCorrectRatingExcetion(wkCurseWords);
		}

		if ( rating.doubleValue() < CustomerReviewConstants.DEFAULTS.MINIMAL_RATING )
			throw new FoundCurseWordsExcetion(wkCurseWords);

		CustomerReviewModel customerReviewModel = customerReviewService
				.createCustomerReview( rating, comment, paramUserModel,
						paramProductMode );

		return customerReviewModel;
	}

}

