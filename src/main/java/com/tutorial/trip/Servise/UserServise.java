package com.tutorial.trip.Servise;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.tutorial.trip.dto.BookingStatusDto;

import com.tutorial.trip.dto.NearByDestnitationDto;
import com.tutorial.trip.dto.PartnerDto;
import com.tutorial.trip.dto.PartnersRequestedDto;
import com.tutorial.trip.dto.SignInInput;
import com.tutorial.trip.dto.SignUpInput;
import com.tutorial.trip.model.Authentication;
import com.tutorial.trip.model.Booking;
import com.tutorial.trip.model.District;
import com.tutorial.trip.model.HisIntrest;
import com.tutorial.trip.model.HotelsAvailableToStay;
import com.tutorial.trip.model.MainAttractionsInYourDestination;
import com.tutorial.trip.model.MeetingRequest;
import com.tutorial.trip.model.StatesWeHaveServise;
import com.tutorial.trip.model.TravelDestinations;
import com.tutorial.trip.model.User;
import com.tutorial.trip.model.UserPortfolio;
import com.tutorial.trip.repository.AuthenticationRepo;
import com.tutorial.trip.repository.BookingRepo;
import com.tutorial.trip.repository.DestinationRepo;
import com.tutorial.trip.repository.DistrictRepository;
import com.tutorial.trip.repository.HisIntrestRepo;
import com.tutorial.trip.repository.HotelsAvailableToStayRepo;
import com.tutorial.trip.repository.MainAttractionRepo;
import com.tutorial.trip.repository.MeetingRequestRepo;
import com.tutorial.trip.repository.StateRepo;
import com.tutorial.trip.repository.UserProfileRepo;
import com.tutorial.trip.repository.UserRepository;

@Service
public class UserServise {
	
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	@Autowired
	
	private HisIntrestRepo hisIntrestRepo;
	
	@Autowired
	private AuthenticationRepo authenticationRepo;
	
	@Autowired
	private DistrictRepository districtRepo;
	
	@Autowired
	private DestinationRepo destinationRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private HotelsAvailableToStayRepo hotelsAvailableRepo;
	
	@Autowired
	
	private BookingRepo bookingRepo;
	
	@Autowired
	private MainAttractionRepo mainAttractionRepo;
	
	@Autowired
	private MeetingRequestRepo meetingRequestRepo;
	
	
	
	
	
	
	//signup

	    public ResponseEntity<String> signUp(SignUpInput signUpInput) {
		
		String userEmail=signUpInput.getUserEmail();
		
		User user=userRepo.findFirstByUserEmail(userEmail);

		
		if(user!=null) {return new ResponseEntity<String>("User Already Exist",HttpStatus.BAD_REQUEST); }
		
		String password=signUpInput.getUserPassword();
		
		String encryptedPassword=encryptPassword(password);
		
		
		//save user
		  user=User.builder().userEmail(signUpInput.getUserEmail()).userName(signUpInput.getUserName()).userPassword(encryptedPassword)
				.build();
		  
		  userRepo.save(user);
		  
		 
		  
		  
		
		 // save userportfolio
		   UserPortfolio userProfile=UserPortfolio.builder().preferredLanguage(signUpInput.getPreferredLanguage()).
		
				   regularWeekEndPlans(signUpInput.getRegularWeekEndPlans())
				   .userAge(signUpInput.getUserAge()).userState(signUpInput.getUserState()).userJob(signUpInput.getUserJob())
				   .userSex(signUpInput.getUserSex()).userStatus(signUpInput.getUserStatus()).user(user).userCity(signUpInput.getUserCity())
				   .build();
		 
		   userProfileRepo.save(userProfile);
		   
		   
		   
		   
		   
		   //save hisIntrest
			  
		 
			  for(HisIntrest intrest:signUpInput.getListOfuserIntrest()) {
				  
				  intrest.setUserProfile(userProfile);
				  
				  hisIntrestRepo.save(intrest);
				  
			  }
		  		   
		return   new ResponseEntity<String>("SignUp sucessfully",HttpStatus.OK);
		
	    }
		
	
		
		
	


	

    //encrypt password
      private String encryptPassword(String password) {
      
	     String salt = BCrypt.gensalt();

	     
	     String hashedPassword = BCrypt.hashpw(password, salt);

	     return hashedPassword;
	 }

      //verify password
      
	   private boolean verifyPassword(String password, String hashedPassword) {
	    return BCrypt.checkpw(password, hashedPassword);
	}
  

		   
		   //signin

		public ResponseEntity<String> signIn(SignInInput signInInput) {
			
			String userEmail=signInInput.getUserEmail();
			
			User user=userRepo.findFirstByUserEmail(userEmail);
		        if(user==null) {return new ResponseEntity<String>("user not exit",HttpStatus.BAD_REQUEST); }
		        
		        String inputedPassword=signInInput.getUserPassword();
		        
		        String encryptedPasswordFromUser=user.getUserPassword();
		        
		        Boolean passwordVerification=verifyPassword(inputedPassword,encryptedPasswordFromUser);
		        
		        if(passwordVerification==false) {return new ResponseEntity<String>("SignIn Failed",HttpStatus.BAD_REQUEST); }
			
			
		        Authentication auth=new Authentication(user);
		         authenticationRepo.save(auth);
		         
		         return new ResponseEntity<String>(auth.getToken(),HttpStatus.BAD_REQUEST);
		        
		        
		}

		public List<NearByDestnitationDto> findNearByDestination(String token, String email) {
			
			List<NearByDestnitationDto>allNearByDestination=new ArrayList<>();
			
			Boolean verifyEmailAndToken=verify(token,email);
			if(verifyEmailAndToken==false) {
				
				return allNearByDestination;
			}
			User user=userRepo.findFirstByUserEmail(email);
			Long userId=user.getUserId();
			UserPortfolio profile=userProfileRepo.findByUserId(userId);
			
			String userCity=profile.getUserCity();
			
			District district=districtRepo.findByName(userCity);
			
			List<NearByDestnitationDto>nearDestinations=findDestination(district);
			
			
			
			
			
			
			return nearDestinations;
		}
		
		
		
		
		//to find the near by destinations

		private List<NearByDestnitationDto> findDestination(District district) {
			
			List<TravelDestinations>getDistrictsOfSameState=destinationRepo.findAll();
			
			List<NearByDestnitationDto>getNearByDistrict=new ArrayList<>();
			
			
			Double longitude=district.getLongitude();
			
			Double latitude=district.getLatitude();
			
			HashMap<Double,TravelDestinations>map=new HashMap<>();
			
			for(TravelDestinations dis:getDistrictsOfSameState) {
				
				
				Double distance=findDistance(longitude,latitude,dis.getLongitude(),dis.getLatitude(),6371.0);

				
				map.put(distance, dis);}
			
			
			Double smallest=Double.MAX_VALUE;
			Double secondSmallest=Double.MAX_VALUE;
			Double thirdSmallest=Double.MAX_VALUE;
			
			
			for(Double key:map.keySet()) {
				
				if(key<smallest) {
					
					
					thirdSmallest=secondSmallest;
					secondSmallest=smallest;
					smallest=key;
					
					
				}
				
				else if(key<secondSmallest) {
					
					
					thirdSmallest=secondSmallest;
					
					secondSmallest=key;
				}
				
				else if(key<thirdSmallest) {
					thirdSmallest=key;
				}
			}
			
			
			TravelDestinations travelDest1=map.get(smallest);
			NearByDestnitationDto destinationDto1=NearByDestnitationDto.builder().destinationName(travelDest1.getDestinationName())
					.famousFor(travelDest1.getFamousFor()).timeToVist(travelDest1.getTimeToVist()).build();
			
			
			TravelDestinations travelDest2=map.get(secondSmallest);
			
			NearByDestnitationDto destinationDto2=NearByDestnitationDto.builder().destinationName(travelDest2.getDestinationName())
					.famousFor(travelDest2.getFamousFor()).timeToVist(travelDest2.getTimeToVist()).build();
			
			

			TravelDestinations travelDest3=map.get(thirdSmallest);
			NearByDestnitationDto destinationDto3=NearByDestnitationDto.builder().destinationName(travelDest3.getDestinationName())
					.famousFor(travelDest3.getFamousFor()).timeToVist(travelDest3.getTimeToVist()).build();
			
			
			
			
			
			
				
			getNearByDistrict.add(destinationDto1);
			
			getNearByDistrict.add(destinationDto2);
			
			
			getNearByDistrict.add(destinationDto3);
					
			
			return getNearByDistrict;
		}

		//find distace b/w user's area and all available destinations and give him 3 nearest locations
		
		private Double findDistance(Double lon1, Double lat1, Double lon2, Double lat2,Double earthradius) {
			
		
		    Double dLat = Math.toRadians(lat2 - lat1);
	        Double dLon = Math.toRadians(lon2 - lon1);
	        
	        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

	        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        Double distance = earthradius* c;

	        return distance;
	        
			
		}

		
		//verify token and email
		
		
		private Boolean verify(String token, String email) {
			
			
			
			
			
			if(token==null && email==null){
	            return false;
	        }

	        Authentication authToken = authenticationRepo.findFirstbyToken(token);

	        if(authToken==null){
	            return false;
	        }

	        String expectedEmail = authToken.getUser().getUserEmail();


	        return expectedEmail.equals(email);
	    }
		
		
		
		
		//to get destinations by states

		public List<NearByDestnitationDto> getDestinationByState(String state, String token, String email) {
			
			
            List<NearByDestnitationDto>allDestination=new ArrayList<>();
			
			 Boolean verifyEmailAndToken=verify(token,email);
			if(verifyEmailAndToken==false) {
				
				return allDestination;
			}
			
			StatesWeHaveServise statesWehaveService=stateRepo.findFirstByStateName(state);
			
			
			if(statesWehaveService==null) {
				
				return allDestination;
			}
			
			Long stateId=statesWehaveService.getStateId();
			
			
			 List<TravelDestinations>allDestinationinTravelDestination=new ArrayList<>();
			
			 allDestinationinTravelDestination=destinationRepo.getAllDestinationByStateId(stateId);
			 
			 
			 for(TravelDestinations td: allDestinationinTravelDestination) {
				 
				 NearByDestnitationDto nd=NearByDestnitationDto.builder().destinationName(td.getDestinationName())
						 .famousFor(td.getFamousFor()).timeToVist(td.getTimeToVist()).build();
				 
				 
				 allDestination.add(nd);
				 
				 
			 }
		
			
			return  allDestination;
		}

		public List<HotelsAvailableToStay> getHotels(String destinationname, String token, String email) {
		
			List<HotelsAvailableToStay>allHotels=new ArrayList<>();
			
			 Boolean verifyEmailAndToken=verify(token,email);
			if(verifyEmailAndToken==false) {
				
				return allHotels;
			}
			TravelDestinations travelDestination=destinationRepo.findFirstByDestinationName(destinationname);
			
			if(travelDestination==null) {
				return allHotels;
			}
			
			Long destId=travelDestination.getDestID();
			
			allHotels=hotelsAvailableRepo.getHotels(destId);
			
			
			return allHotels;
		}

		public ResponseEntity<String> bookHotel(String token, String email, Booking booking) {
			
			
			 Boolean verifyEmailAndToken=verify(token,email);
				if(verifyEmailAndToken==false) {
					
					return new ResponseEntity<String>("verification failed",HttpStatus.BAD_REQUEST);
				}
				
			//check destination exist
				
				TravelDestinations travelDestionation=destinationRepo.findFirstByDestinationName(booking.getDestinationName());
				if(travelDestionation==null) {
					
					return new ResponseEntity<String>("we dont have service in the given destination",HttpStatus.BAD_REQUEST);
				}
				
				HotelsAvailableToStay hotel=hotelsAvailableRepo.findByHotelNameAndDestinationId(booking.getHotelName(),travelDestionation.getDestID());
				if(hotel==null) {
					
					return new ResponseEntity<String>("Hotel You provided is not correct",HttpStatus.BAD_REQUEST);
					
				}
				//to check the room availability
				
				
				Integer roomsAvailable=checkForAvailablity(hotel,booking);
				
				
				
				
				//check if rooms are available
				if(roomsAvailable<1) {
					return new ResponseEntity<String>("No rooms available for the hotel you select so choose another hotel",HttpStatus.BAD_REQUEST);
				}
				
				
				
				BookingStatusDto status=new BookingStatusDto();
				
				//set email
				
				booking.setEmail(email);
				//save booking
				
				booking.setToken(status.getBookingToken());
				
				bookingRepo.save(booking);
				
			
				return new ResponseEntity<String>(booking.getToken(),HttpStatus.OK);}

		
		private Integer checkForAvailablity(HotelsAvailableToStay hotel, Booking booking) {
			
			List<Booking>allBookingOfTheSelectHotel=bookingRepo.findByHotelNameAndDestinationName(hotel.getHotelName(),booking.getDestinationName());
			
			Integer numberOfRooms=hotel.getTotalRooms();
			
			
			
			for(Booking booKing:allBookingOfTheSelectHotel) {
				
				LocalDate visitDate=booKing.getDateOfVisit();
				
				
				
				
				LocalDate checkOutDate=booKing.getCheckOutDay();
			
				
				if(visitDate==null||checkOutDate==null) {
					
					return -1;
				}
				
				int comparision1=visitDate.compareTo(booking.getCheckOutDay());
		
				int comparision2=checkOutDate.compareTo(booking.getDateOfVisit());

				if(comparision1<0&&comparision2>0) {
					
					numberOfRooms=numberOfRooms-1;
					
				}
				
				
				
			}
			
			
			return numberOfRooms;
		}

		public List<MainAttractionsInYourDestination> getNearByAttractions(String token) {
			
			
			List<MainAttractionsInYourDestination>listOfAttractions=new ArrayList<>();
			//verify token
			
			Booking booking=bookingRepo.findFirstByToken(token);
			
			if(booking==null) {
				
				return listOfAttractions;
			}
			
			 TravelDestinations travelDestination= destinationRepo.findFirstByDestinationName(booking.getDestinationName());
			 
			 List<MainAttractionsInYourDestination>getAttractions=mainAttractionRepo.getByDestID(travelDestination.getDestID());
			 	
			return getAttractions;
		}

		public ResponseEntity<String> deleteBooking(String token) {
			Booking booking=bookingRepo.findFirstByToken(token);
			if(booking==null) {
				
				return new ResponseEntity<String>("your booking token is not valid",HttpStatus.BAD_REQUEST);
			}
			
		
			
			bookingRepo.delete(booking);
			
			
			return new ResponseEntity<String>("Checkout sucessfully,Happy to have You here",HttpStatus.OK);
		}

		
		
		
		
		
		public ResponseEntity<String> updateDates(String visitdate, String checkoutdate,String token) {
			
			Booking booking=bookingRepo.findFirstByToken(token);
			if(booking==null) {
				
				return new ResponseEntity<String>("your booking token is not valid",HttpStatus.BAD_REQUEST);
			}
			
			
			LocalDate parsedVisitDate = LocalDate.parse(visitdate, DateTimeFormatter.ISO_DATE);
		    LocalDate parsedCheckoutDate = LocalDate.parse(checkoutdate, DateTimeFormatter.ISO_DATE);
		    
		    String hotelName=booking.getHotelName();
		    String destinationName=booking.getDestinationName();
		    
		    TravelDestinations destination=destinationRepo.findFirstByDestinationName(destinationName);
		    Long destId=destination.getDestID();
			
			HotelsAvailableToStay hotel=hotelsAvailableRepo.findByHotelNameAndDestinationId(hotelName,destId);
			Booking newBooking=Booking.builder().checkOutDay(parsedCheckoutDate).dateOfVisit(parsedVisitDate).destinationName(destinationName).build();
			
			Integer numberOfRooms=checkForAvailablity(hotel, newBooking);
			if(numberOfRooms<1) {
				
				return new ResponseEntity<String>("No Room available of the given date give another date or stay with your initial decision",HttpStatus.OK);
				
			}

			
			bookingRepo.updateDatesByToken( parsedVisitDate,parsedCheckoutDate,token);
			
			
			return new ResponseEntity<String>("Your slot has been changed",HttpStatus.OK);
		}

		
		
		
		
		public List<PartnerDto> findPartner(String bookingtoken) {
		
			List<PartnerDto>listOfPartners=new ArrayList<>();
			
			Booking booking=bookingRepo.findFirstByToken(bookingtoken);
			if(booking==null) {
				
				return listOfPartners;
			
			}
			
			Long bookingId=booking.getBookingId();
			
			List<Booking>getBookingOfAllInSameLocation=bookingRepo.findBookingsOfAllInSameLocation(bookingId,booking.getDestinationName());
			
			if(getBookingOfAllInSameLocation==null) {
				
				return listOfPartners;
			}
			LocalDate vistDate=booking.getDateOfVisit();
			System.out.println(vistDate);
			
			LocalDate checkoutDate=booking.getCheckOutDay();
			System.out.println(checkoutDate);
			
			listOfPartners=findMyFriend(vistDate,checkoutDate,getBookingOfAllInSameLocation);
			
			
			
			
			return listOfPartners;
		}

		private List<PartnerDto> findMyFriend(LocalDate myVisitDate, LocalDate myCheckoutDate,
				List<Booking> getBookingOfAllInSameLocation) {
		
			List<PartnerDto>userFound=new ArrayList<>();
			//long numOfDays = ChronoUnit.DAYS.between(myVisitDate, myCheckoutDate);
			
			long numOfDays = myVisitDate.until(myCheckoutDate, ChronoUnit.DAYS);
			for(Booking booking:getBookingOfAllInSameLocation) {
			
			List<LocalDate>availableDates=new ArrayList<>();
			int comparison1 = myCheckoutDate.compareTo(booking.getDateOfVisit());
			System.out.println(comparison1);
			
			int comparison2=booking.getCheckOutDay().compareTo(myVisitDate);
			System.out.println(comparison2);
			
			if(comparison1>0&&comparison2>0) {
				
				if(myVisitDate.isAfter(booking.getDateOfVisit())) {
					long j=0;
					for(long i=0;i<=numOfDays;i++) {
						
						LocalDate myDate=myVisitDate.plusDays(i);
						System.out.println(myDate);
						
						LocalDate hisDate=booking.getDateOfVisit().plusDays(j);
						System.out.println(hisDate);
						j++;
						i--;
						
						
						
						if(myDate.isEqual(hisDate)) {
							
							
							int comparision=myDate.compareTo(booking.getCheckOutDay());
							if(comparision>0) {
								
								break;
							}
								
							availableDates.add(myDate);
							
							i++;
						}
						
						
					}
					
				}
				else  {
					long j=0;
					for(long i=0;i<=numOfDays;i++) {
						
						
                         LocalDate myDate=myVisitDate.plusDays(i);
						
						 LocalDate hisDate=booking.getDateOfVisit().plusDays(j);
						 
						 if(myDate.isEqual(hisDate)) {
							 
							 int comparision=myDate.compareTo(booking.getCheckOutDay());
								if(comparision>0) {
									
									break;
								}
							 
								availableDates.add(myDate);
								j++;
							 
						 }
						 
						
						
					}
					
					
				}
				
				
				
			}
			if(availableDates.size()>0) {
				
				String email=booking.getEmail();
				
				User user =userRepo.findFirstByUserEmail(email);
				Long userId=user.getUserId();
				UserPortfolio userProfile=userProfileRepo.findByUserId(userId);
				List<HisIntrest>listOfHisIntrest=hisIntrestRepo.findByprofileID(userProfile.getProfileId());
				
				PartnerDto partner=PartnerDto.builder().availableDates(availableDates).preferredLanguage(userProfile.getPreferredLanguage())
						.regularWeekEndPlans(userProfile.getRegularWeekEndPlans()).userAge(userProfile.getUserAge()).userCity(userProfile.getUserCity())
						.userJob(userProfile.getUserJob()).userName(user.getUserName()).userSex(userProfile.getUserSex()).userState(userProfile.getUserState())
						.userStatus(userProfile.getUserStatus()).email(user.getUserEmail()).listOfIntrest(listOfHisIntrest).build();
				
				userFound.add(partner);
				
			}
			
			
			
			
			}
			
			return userFound;
		}

		public ResponseEntity<String> postMeeting(String bookingtoken, MeetingRequest meetingRequest) {
			
			Booking booking=bookingRepo.findFirstByToken(bookingtoken);
			if(booking==null) {
				
				return new ResponseEntity<String>("invalid bookingtoken",HttpStatus.BAD_GATEWAY);
			
			}
		
			
			
			
			
			
			String userEmail=booking.getEmail();
			
			User user=userRepo.findFirstByUserEmail(userEmail);
			Long userId=user.getUserId();
			
			
			//need to check if i already got a request from him or not
			
			MeetingRequest req=findThatReqFromDataBase(userEmail,meetingRequest.getFriendEmail());
			if(req!=null) {
				
				return new ResponseEntity<String>("You already got a request from him/her check that first ",HttpStatus.OK);
			}
			
			
			
			MeetingRequest request=meetingRequestRepo.findByuserID(userId);
			if(request!=null) {
				return new ResponseEntity<String>("you are already schedule a meeting wait for it's outcome",HttpStatus.OK);
				
			}
			
			meetingRequest.setUser(user);
			
			meetingRequestRepo.save(meetingRequest);
			
			
			return new ResponseEntity<String>("Send the meeting request",HttpStatus.OK);
		}

		
		
		//to find before requesting like that guy send me any request or not
		private MeetingRequest findThatReqFromDataBase(String userEmail, String friendEmail) {
			
			User thatUser=userRepo.findFirstByUserEmail(friendEmail);
			Long userId=thatUser.getUserId();
			
			MeetingRequest request=meetingRequestRepo.findByEmailAndUserId(userEmail,userId);
			
			return request;
		}

		public List<PartnersRequestedDto> getTheMeetingRequests(String bookingtoken) {
			
             List<PartnersRequestedDto>listOfPartners=new ArrayList<>();
			
			Booking booking=bookingRepo.findFirstByToken(bookingtoken);
			if(booking==null) {
				
				return listOfPartners;
			
			}
			
			String userEmail=booking.getEmail();
			
			List<Long>getAllFriendsId=meetingRequestRepo.findAllFriendsIdByEmail(userEmail);
			
			List<PartnersRequestedDto>allFriendsWanttoMeetme=getMyFriends(getAllFriendsId);
			
			
			
			return allFriendsWanttoMeetme;
		}

		
		
		
		private List<PartnersRequestedDto> getMyFriends(List<Long> getAllFriendsId) {
			
			List<PartnersRequestedDto>allFriendsWantstoMeetme=new ArrayList<>();
			
			for(Long userId:getAllFriendsId) {
				
				UserPortfolio profile=userProfileRepo.findByUserId(userId);
			String userName=profile.getUser().getUserName();
				List<HisIntrest>hisIntrestList=hisIntrestRepo.findByprofileID(profile.getProfileId());
				
				PartnersRequestedDto partners=PartnersRequestedDto.builder().listOfIntrest(hisIntrestList).regularWeekEndPlans(profile.getRegularWeekEndPlans())
						.userAge(profile.getUserAge()).preferredLanguage(profile.getPreferredLanguage()).userCity(profile.getUserCity())
						.userCity(profile.getUserCity()).userJob(profile.getUserJob()).userName( userName).userSex(profile.getUserSex()).userStatus(profile.getUserStatus())
						.userState(profile.getUserState()).userEmail(profile.getUser().getUserEmail()).build();
				
				allFriendsWantstoMeetme.add(partners);
				
			}
			
			
			
			return allFriendsWantstoMeetme;
		}

		public ResponseEntity<String> selectFriend(String friendEmail, String bookingtoken) {
			
			Booking booking=bookingRepo.findFirstByToken(bookingtoken);
			if(booking==null) {
				
				return new ResponseEntity<String>("invalid bookingtoken",HttpStatus.BAD_GATEWAY);
			
			}
			User user=userRepo.findFirstByUserEmail(friendEmail);
			Long userId=user.getUserId();
			String myEmail=booking.getEmail();
			
			meetingRequestRepo.DeleteUnwantedRequest(myEmail,userId);
			
			
			
			return new ResponseEntity<String>("your meeting with "+user.getUserName()+" is made",HttpStatus.OK);
			
			
		}

		public ResponseEntity<String> getMyMeetingStatus(String bookingtoken) {
			
			Booking booking=bookingRepo.findFirstByToken(bookingtoken);
			if(booking==null) {
				
				return new ResponseEntity<String>("invalid bookingtoken",HttpStatus.BAD_GATEWAY);
			
			}
			String Myemail=booking.getEmail();
			
			User user=userRepo.findFirstByUserEmail(Myemail);
			Long userId=user.getUserId();
			
			MeetingRequest meetingRequest=meetingRequestRepo.findByuserID(userId);
			if(meetingRequest==null) {
				
				return new ResponseEntity<String>("Not accepted your request",HttpStatus.OK);
			}
			
			String friendEmail=meetingRequest.getFriendEmail();
			User guyIWantToMeet=userRepo.findFirstByUserEmail(friendEmail);
			
			List<MeetingRequest>getAllMeetingOfWithFriendEmail=meetingRequestRepo.findByFriendEmail(friendEmail);
			
			if(getAllMeetingOfWithFriendEmail.size()==1) {
				
				
				return new ResponseEntity<String>("Your meeting with "+guyIWantToMeet.getUserName()+" is scheduled",HttpStatus.OK);
			}
			
			
			return new ResponseEntity<String>("Your meeting with "+guyIWantToMeet.getUserName()+" is still under consideration wait for some more time",HttpStatus.OK);
			
		}









		public ResponseEntity<String> signOut(String email, String token) {
			
			
			Boolean verifyEmailAndToken=verify(token,email);
			
			if(verifyEmailAndToken==false) {
				
				return new ResponseEntity<String>("verification failed",HttpStatus.BAD_REQUEST);
			}
			
			Authentication authentication=authenticationRepo.findFirstbyToken(token);
			
			authenticationRepo.delete(authentication);
			
			
			return new ResponseEntity<String>("Signout sucess",HttpStatus.OK);
		}
		 
		
		
				
				
			
			
			
		
		        
		        
			
			
			

		

	
	
}
