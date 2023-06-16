# Welcome to readme-md-generator &#x1F44B; 
![example workflow](https://img.shields.io/badge/Eclipse-Version%3A%202022--09%20(4.25.0)-orange)
![example workflow](https://img.shields.io/badge/SpringBoot-2.2.1-yellowgreen)
![example workflow](https://img.shields.io/badge/Java-8-yellowgreen)
![example workflow](https://img.shields.io/badge/Postman-v10.13-orange)
![example workflow](https://img.shields.io/badge/Documentation-Yes-green)
![example workflow](https://img.shields.io/badge/Manitained%3F-Yes-green)
 >CLI that generate beautiful **ReadME**.md files

  :house:  <b><span style="color: blue;">Homepage</span></b>
  


 # Prerequisties

 - **Eclipse >=4.55.0**
 - **Postman >=10.13**
 


# Install
```
Maven Install
SpringTool Install
```
 # Framework And Language

 - **Framework :  SpringBoot**
 - **Language :  Java**

 # Dependencies Required

 
 - **spring-boot-starter-web**
 - **spring-boot-devtools**
 - **spring-boot-starter-data-jpa**
 - **spring-boot-starter-validation**
 
 - **mysql-connector**
 - **lambok**
 - **jbcrypt**

 - **spring-boot-starter-test**
 


# Models Used



 - **User**
 -  **Admin**
 -  **Authentication**
 -  **District**
 -  **Booking**
 -  **GeolocationModel**
 -  **HisIntrest**
 -  **HotelsAvailabletoStay**
 -  **MainAttractionInYourDestination**
 -  **MeetingRequest**
 -  **StatesWehaveService**
 -  **TravelDestination**
 -  **UserProfile**
 


	
	



#  Data flow

- **User send a request to ApI endpoint**
- **api forward it to the controller**
- **controller forward it to the Service layer**
- **service layer provide the necessary business logic and ask the repository for data**
- **Repository fetch the data from Mysql and give it back to service layer**
- **service layer give it to controller**
- **contoller give it to api**
- **Api give the response to user**


#  Api end points used at Admin Controller

- **admin/add**
- **admin/add/states**
- **admin/add/destnation**
- **admin/delete/destination**
- **admin/get/destinations/stateid/{id}**
- **admin/add/hotel/destid/{id}**
- **admin/get/hotels/destnationname/{name}**
- **admin/add/attraction/{destination}**
- **admin/get/attractions/{destId}**


#  Api end points used at District Controller

- **districts/add**
- **districts/get**


#  Api end points used at User Controller

- **user/signup**
- **user/signin**
- **user/nearby/destination/{token}/{email}**
- **user/destination/state/{state}/{token}/{email}**
- **user/hotels/destination/{destinationname}/{token}/{email}**
- **user/booking/{token}/{email}**
- **user/get/nearby/attraction/{bookingtoken}**
- **user/update/dateofvisit/{date}/checkoutday/{checkout}/{bookingtoken}**
- **user/delete/booking/{bookingtoken}**
- **user/get/partner/{bookingtoken}**
- **user/post/meeting/{bookingtoken}**
- **user/get/meeting/status/{bookingtoken}**
- **user/get/mettingRequest/{bookingtoken}**
- **user/select/friend/{friendemail}/{bookingtoken}**
- **user/signout/{email}/{token}**





# About my SolotripPlanner Project

 
I'm excited to announce the completion of my backend for the Solo Trip Planner project. I would like to share the details with all of you. In our current scenario, solo trips to our favorite destinations provide a much-needed relief and rejuvenation. Considering the user's needs and time constraints, my project offers a rich set of features to assist them throughout their journey.

The project begins with a user signup process, where users are asked to provide detailed information about themselves. After signing up, users can securely sign in and access the platform. From there, I assist them by providing information about the nearest destinations available. Users can filter destinations based on states and search for hotels in their desired location. To simplify the hotel booking process, I have implemented a backend algorithm that helps users book the hotel of their choice. Upon successful booking, users receive a token that allows them to manage their check-in and check-out dates.

One of the key functionalities of the project is the ability to find a travel partner in the user's location. Users can view all available partners and the dates they are available, thanks to the algorithm I have developed. To initiate a meetup, users can send a request to a potential partner, and they will need to wait for confirmation from the other party to avoid any confusion. Moreover, the algorithm prevents users from sending requests to someone who has already sent them a request.

The platform also provides users with visibility into the number of meetup requests they have received. This feature allows users to select the person they would like to meet. Once a request is accepted, both parties can see the confirmation.

In conclusion, my Solo Trip Planner project aims to enhance the solo travel experience by offering a comprehensive set of features. I am proud to have developed a backend that enables users to plan their trips efficiently and find suitable travel partners along the way.
.


#   User Controller
```

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserServise userServise;
	
	//signup
	
	
	@PostMapping("/signup")
	
	
	public ResponseEntity<String>signUp(@RequestBody SignUpInput signUpInput){
		
		
		return userServise.signUp(signUpInput);
	}
	
	
	
	//signin
	
	
	@PostMapping("/signin")
	
	public ResponseEntity<String>signIn(@RequestBody SignInInput signInInput){
		
		
		return userServise.signIn(signInInput);
	}
	
	
	//find you 3 nearest destination from your location
	
	
	@GetMapping("/nearby/destination/{token}/{email}")
	
	public List<NearByDestnitationDto>findNearByDestination(@PathVariable("token")String token,@PathVariable("email") String email){
		
		
		return userServise.findNearByDestination(token,email);
	}
	
	

    //filter destination based on states	
	
	
	@GetMapping("/destination/state/{state}/{token}/{email}")
	
	public List<NearByDestnitationDto>findDestinationByState(@PathVariable("state") String state,@PathVariable("token")String token,@PathVariable("email")String email){
			
	
		    return userServise.getDestinationByState(state,token,email);
		
			}
	
	
	
	//filter hotels based on destination
	
	
	@GetMapping("hotels/destination/{destinationname}/{token}/{email}")
	
	public List<HotelsAvailableToStay>getHotels(@PathVariable("destinationname") String destinationname,@PathVariable("token")String token,@PathVariable("email")String email){
		
		
		return userServise.getHotels(destinationname,token,email);
	}
	
	
	
	//book the hotel you want
	
	
	@PostMapping("/booking/{token}/{email}")
	public ResponseEntity<String>bookHotel(@PathVariable("token") String token,@PathVariable("email") String email,@RequestBody Booking booking){
		
		
		return userServise.bookHotel(token,email,booking);
	}
	
	
	//will give the valid booked person further assist like what are the main attraction nearby him
	
	
	@GetMapping("get/nearby/attraction/{bookingtoken}")
	
	public List<MainAttractionsInYourDestination>getAllAttractions(@PathVariable ("bookingtoken")String token){
	
		
	   return userServise.getNearByAttractions(token);
	}
	

	//can update the booking dates if he wants
	
	@PutMapping("update/dateofvisit/{date}/checkoutday/{checkout}/{bookingtoken}")
	
	public ResponseEntity<String>updateDates(@PathVariable("date") String visitdate,@PathVariable("checkout")String checkoutdate,@PathVariable("bookingtoken")String token ){
		
		
		return userServise.updateDates(visitdate,checkoutdate,token);
	}
	
	
	//delete the booking when he checkout
	
	@DeleteMapping("/delete/booking/{bookingtoken}")
	
	public ResponseEntity<String>CheckOut(@PathVariable("bookingtoken")String token){
		
		return userServise.deleteBooking(token);
	}
	
	
	
	//assit the booked person to find his partner within same  destination and overlapping dates
	
	@GetMapping("/get/partner/{bookingtoken}")
	
	public List<PartnerDto>findaPartner(@PathVariable("bookingtoken") String bookingtoken){
		
		
		return userServise.findPartner(bookingtoken);
	}
	
	//can send request to meet the person that u find attractive by looking the profile,one can only
	//send one request at a time and need to wait for the result
	
	@PostMapping("/post/meeting/{bookingtoken}")
	
	public ResponseEntity<String>postMeeting(@PathVariable("bookingtoken") String bookingtoken,@RequestBody MeetingRequest meetingRequest){
		
		return userServise.postMeeting(bookingtoken,meetingRequest);
	}
	
	
	//check his meeting status to know is the other guy accept it or not,or still under consideration
	
	@GetMapping("get/meeting/status/{bookingtoken}")
	
	public ResponseEntity<String>getStatus(@PathVariable("bookingtoken") String bookingtoken){
		
		
		return userServise.getMyMeetingStatus(bookingtoken);
		
	}
	
	//also he can check did he get any request or not
	
	@GetMapping("get/mettingRequest/{bookingtoken}")
	
	public List<PartnersRequestedDto>getTheMeetingRequest(@PathVariable("bookingtoken")String bookingtoken){
		
		
		return userServise.getTheMeetingRequests(bookingtoken);
	}
	
	
	//delete the unwanted request and keep only one meeting,so other guy can also confirm his status
	
	@DeleteMapping("select/friend/{friendemail}/{bookingtoken}")
	public ResponseEntity<String>SelectOneFriend(@PathVariable("friendemail")String friendEmail,@PathVariable("bookingtoken")String bookingtoken){
		
		
		return userServise.selectFriend(friendEmail,bookingtoken);
	}
	
	
	//for signout
	@DeleteMapping("signout/{email}/{token}")
	
	public ResponseEntity<String>signout(@PathVariable("email")String email,@PathVariable ("token")String token){
		
		return userServise.signOut(email,token);
	}
	


}

```



#   Admin Controller
```

@RestController
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private AdminService adminService;
	
	
	//to add admin
	@PostMapping("/add")
	public ResponseEntity<String>addAdmin(@RequestBody Admin admin){
		
		
		return adminService.addAdmin(admin);
		
	}
	
	//admin can add states we have servise
	@PostMapping("/add/states")
	
	public ResponseEntity<String>addState(@RequestBody StatesWeHaveServise state){
		
		return adminService.addState(state);
	}
	
	
	//admin can add destination for each states
	@PostMapping("/add/destnation")
	
	public ResponseEntity<String>addDestination(@RequestBody DestinationDto destination){
		
		return adminService.addDestination(destination);
	}
	
	//admin can delete destination
	@DeleteMapping("/delete/destination")
	
	
	public ResponseEntity<String>deleteDestinations(){
		
		
		return adminService.deleteAllDestination();
	}
	
	//admin can filter destination based on states
	@GetMapping("/get/destinations/stateid/{id}")
	
	public List<TravelDestinations> getDestinationsByState(@PathVariable Long id){
		
		return adminService.getDestinationsByState(id);
	}
	
	
	//admin can add hotels available in each destination
	@PostMapping("/add/hotel/destid/{id}")
	
	public ResponseEntity<String>addHotel(@PathVariable("id") Long id,@RequestBody RestrauntDto restrauntDto){
		
		
		return adminService.addRestraunt(id,restrauntDto);
		
	}
	
	
	//can filter hotels based on destination
	@GetMapping("get/hotels/destnationname/{name}")
	
	public List<HotelsAvailableToStay>getHotels(@PathVariable ("name") String name){
		
		
		return adminService.getHotels(name);
		
	}
	
	//add attraction or main tourist areas in each destibation
	@PostMapping("/add/attraction/{destination}")
	
	public ResponseEntity<String>addAttractions(@PathVariable ("destination")String destination,@RequestBody MainAttractionsInYourDestination attractions){
		
		return adminService.addAttractions(destination,attractions);
		
	}
	
	
	//filter attraction based on destination
	@GetMapping("get/attractions/{destId}")
	public List<MainAttractionsInYourDestination>getAttratactionByDestnationId(@PathVariable("destId") Long id){
		
		return adminService.getAttratactionByDestnationId(id);
		
	}
	
	
	

	
	
	
	
	
}


```


#   District Controller
```


	@RestController
	
	public class DistrictController {
	    

	    @Autowired
	    
       private  DistrictService districtService;

	  
	    
	    @PostMapping("districts/add")
	    public ResponseEntity<String> createDistricts(@RequestBody List<District> districts) {
	        districtService.saveAllDistricts(districts);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Districts created successfully.");
	    }

	    @GetMapping("districts/get")
	    public List<District> getAllDistricts() {
	        return districtService.getAllDistricts();
	    }
	}

```




	
	


  


	







	



# DataBase Used

<details>
<summary><b><span style="color: white;">Clickme</span></b> &#x1F4F2; </summary>

*Mysql*





# User Database
 - **userid**
 -  **username**
 -  **useremail**
 -  **userpassword**

# UserProfile Database
 - **profileid**
 -  **prefferedlanguage**
 -  **regularweekendplans**
 -  **userage**
 -  **city**
 -  **job**
 -  **sex**
 -  **status**
 -  **state**
 -  **fk_userID**

# Authentication Database
 - **authid**
 -  **token**
 -  **tokencreationdate**
 -  **fk_userid**


# State Database
 - **stateid**
 -  **statename**

# Meeting Database
 - **meetingid**
 -  **friendemail**
 -  **fk_userid**
 

 # Intrest Database
 - **Intrestid**
 -  **Intrest**
 -  **fk_profileid**

 # Hotel Database
 - **hotelid**
 -  **description**
 -  **hotelname**
 -  **totalrooms**
 -  **fk_traveldestId**

 # District Database
 - **Districtid**
 -  **latitude**
 -  **longitude**
 -  **districtname**
 -  **state**

# Destination Database
 - **destid**
 -  **destnationname**
 -  **famourfor**
 -  **latitude**
-  **longitude**
 -  **timetovisit**
 -  **fk_stateid**

# Booking Database
 - **bookingid**
 -  **dayofvisit**
 -  **checkoutday**
 -  **destinationname**
-  **useremail**
 -  **username**
 -  **hotelname**
 -  **bookingtoken**
  
# Attraction Database
 - **attractionid**
 -  **attractionname**
 -  **description**
 -  **distancefromyou**
-  **besttimetovisit**
 -  **fk_destinationId**
 
# Admin Database
 - **adminid**
 -  **adminname**
 -  **adminemail**



</details>






  




# Summary


I have developed a project called SoloTripPlanner, utilizing Spring Boot as the framework and MySQL as the database. This comprehensive application encompasses features such as user authentication and validation, ensuring the security and integrity of user data.
The project also has a solid foundation for future enhancements. It aims to further optimize the booking and matchmaking algorithms, elevating them to an industry-level standard. With a robust user database in place, there is potential to implement advanced algorithms to suggest compatible travel partners, enhancing the solo travel experience even more.






# :handshake:  Contributing
  Contributions,issues and features request are welcome! 
  

  #


  This *README* was generated with &#x2764;&#xFE0F; by <b><span style="color: blue;">readme-md-generator</span></b> 



