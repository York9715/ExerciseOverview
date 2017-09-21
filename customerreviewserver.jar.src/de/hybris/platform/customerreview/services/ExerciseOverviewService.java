package de.hybris.platform.customerreview.services

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;

/**
 * @author Creator
 * @version 1.0
 */
public interface ExerciseOverviewService {
	public int getNumberOfReviews(ProductModel paramProductModel,
			Double ratingFrom, Double ratingTo);

	public CustomerReviewModel createCustomerReview(Double rating,
			String comment, UserModel paramUserModel,
			ProductModel paramProductMode);
}
