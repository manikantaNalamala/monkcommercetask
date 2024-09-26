# MonkcommerceTask

## Technologies
- Java 8
- Spring Boot 2.2.1.RELEASE
- MySQL Database (for  testing)
- Spring Data JPA

  ### Prerequisites
- JDK 8+
- An IDE  (STS)

### Steps to Run the Application
1. Clone the repository:
   git clone https://github.com/manikantaNalamala/monkcommercetask.git

### Method	Endpoint	Description	Request Body
POST	/coupons/insert-coupon	Create a new coupon	Coupon JSON  
GET	/coupons/{id}	Get a coupon by ID	N/A  
DELETE	/coupons/{id}	Delete a coupon by ID	N/A   
POST	/coupons/apply-coupon/{id}	Apply a coupon to a cart and calculate discount	Cart JSON with items

## Coupon Types:
CART_DISCOUNT: Applies discount on the entire cart.  
PRODUCT_DISCOUNT: Applies discount on a specific product.  
BUY_X_GET_Y: Buy X items, get Y items for free.

## Testing the Application
Postman Collection
You can use the provided Postman collection to test all the endpoints. 
Import the Postman collection and run the following requests:

1.Create a coupon.  
2.Apply the coupon to a cart.  
3.Retrieve coupon details.  
4.Delete a coupon.

## Future Enhancements
Implement support for multiple coupon applications in a single transaction.  
Add user authentication and coupon history tracking.    
Integrate with a relational database such as MySQL.  
Need to do more clean & optimized code that can pass the all test cases.
