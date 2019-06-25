# CoordinatorStorytelling-BE
Coordinator Storytelling Backend


# All Get Enpoints are fully functional and deployed to the http://coordinator-storytelling.herokuapp.com/ server

the GET Endpoints are :
 
/stories/all     (no authentication) - returns list of all stories from all coordinators

/stories/5      (no authentication) - returns the story associated with id ("5" in this case)

/stories/mine (requires token) - returns all stories from whomever is logged in

/stories/random (no authentication) - returns random story from a random coordinator

/users/getusername (requires token) - returns the user information for whomever holds the token





# Post endpoints are :


/createnewuser (no authentication) - creates a new user based on a JSON format. The format is 

{
   
   "username": "fakeuser",
  
   "password":"password",
   
   "firstname": "John2",
  
   "lastname": "Doe",
 
   "country": "USA",
 
   "email": "john@doe.com",
  
   "title": "owner"
  }

the username HAS to be unique and it has to have the rest of the information in it. (Required to create the user)

/stories/story (requires a token) - create a new story based on a JSON format. the format is

 {
       
        "title": "fake title posted through postman as a user for the second time",
        
        "country": "Bolivia",
        
        "description": "fakeish description",
        
        "content": "A creative man is motivated by the desire to achieve, not by the desire to beat others",
        
        "date": "fake date"
        
 }
 
 #PUT endpoint :
 
 /stories/story/7 - Updates a current story with any new information
 
 the Json doesnt have to be a full copy of the story as long as it has whats being updated labeled:
 
 {"country":"sample country name"}
 
 will update the "country" section of the story and leave rest intact.
 
 
 #DELETE endpoint:
 
 /stories/story/3 - deletes a specific story based on the id ( for example 3)
 
 #ADMIN ONLY ENDPOINTS
 
 
/stories/username/john - access all stories from a specific user

/users/users - access a list of all users 

/users/user/3 - access all data (except password) for a specific user

PUT /users/user/3 - update a specific users information
 

