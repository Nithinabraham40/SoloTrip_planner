package com.tutorial.trip.controller;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.trip.Servise.UserServise;
import com.tutorial.trip.dto.NearByDestnitationDto;
import com.tutorial.trip.dto.PartnerDto;
import com.tutorial.trip.dto.PartnersRequestedDto;
import com.tutorial.trip.dto.SignInInput;
import com.tutorial.trip.dto.SignUpInput;
import com.tutorial.trip.model.Booking;
import com.tutorial.trip.model.HotelsAvailableToStay;
import com.tutorial.trip.model.MainAttractionsInYourDestination;
import com.tutorial.trip.model.MeetingRequest;

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
	
	@DeleteMapping("signout/{email}/{token}")
	
	public ResponseEntity<String>signout(@PathVariable("email")String email,@PathVariable ("token")String token){
		
		return userServise.signOut(email,token);
	}
	


}
