# cc-cars
A simple car marketplace REST API using Spring Boot

### Data Dump
You can create the database and populate it with sample data by importing the dump file `cc_cars_dump.sql` into MySQL.

### Endpoints:
 - **PUT** `/car/{carId}` - code.climb.cccars.controller.CarController#updateCar(int, Car)
 - **GET** `/listing/{listingId}/comments` - code.climb.cccars.controller.ListingController#getListingComments(int)
 - **GET** `/listing/search` - code.climb.cccars.controller.ListingController#searchListings(String)
 - **POST** `/listing` - code.climb.cccars.controller.ListingController#addListing(ListingDTO)
 - **PUT** `/user/{username}` - code.climb.cccars.controller.UserController#updateUser(String, UserDTO)
 - **GET** `/user/me` - code.climb.cccars.controller.UserController#getCurrentUser()
 - **POST** `/auth/login` - code.climb.cccars.controller.AuthenticationController#login(AuthenticationDTO)
 - **GET** `/car/filter` - code.climb.cccars.controller.CarController#filterCars(String, String, Short, CarGear, CarBodyType, Short, CarWheelDrive, Short)
 - **POST** `/listing/{listingId}/pictures` - code.climb.cccars.controller.ListingController#addListingPicture(int, MultipartFile)
 - **GET** `/car` - code.climb.cccars.controller.CarController#getCarsPage(int, int)
 - **DELETE** `/listing/{listingId}` - code.climb.cccars.controller.ListingController#deleteListing(int)
 - **POST** `/signup` - code.climb.cccars.controller.SignupController#signup(SignupDTO)
 - **PUT** `/listing/{listingId}` - code.climb.cccars.controller.ListingController#updateListing(int, ListingDTO)
 - **POST** `/user/add-admin` - code.climb.cccars.controller.UserController#addAdmin(SignupDTO)
 - **GET** `/car/{carId}` - code.climb.cccars.controller.CarController#getCar(int)
 - **GET** `/user/{username}` - code.climb.cccars.controller.UserController#getUser(String)
 - **GET** `/listing/{listingId}/pictures` - code.climb.cccars.controller.ListingController#getListingPictures(int)
 - **GET** `/car/makes` - code.climb.cccars.controller.CarController#getMakes()
 - **PUT** `/user/me` - code.climb.cccars.controller.UserController#updateCurrentUser(UserDTO)
 - **PUT** `/listing/{listingId}/comments/{commentId}` - code.climb.cccars.controller.ListingController#updateListingComment(int, int, CommentDTO)
 - **GET** `/listing/{listingId}` - code.climb.cccars.controller.ListingController#getListing(int)
 - **POST** `/car` - code.climb.cccars.controller.CarController#addCar(Car)
 - **POST** `/listing/{listingId}/comments` - code.climb.cccars.controller.ListingController#addListingComment(int, CommentDTO)
 - **GET** `/user` - code.climb.cccars.controller.UserController#getUsersPage(int, int)
 - **DELETE** `/car/{carId}` - code.climb.cccars.controller.CarController#deleteCar(int)
 - **GET** `/car/models` - code.climb.cccars.controller.CarController#getModels(String)
 - **DELETE** `/user/{username}` - code.climb.cccars.controller.UserController#deleteUser(String)
 - **GET** `/user/filter` - code.climb.cccars.controller.UserController#filterUsers(String, String, String, String, Role, Boolean, Boolean)

## Postman
You can find examples on all endpoints in the Postman collection file `cc_cars.postman_collection.json`
