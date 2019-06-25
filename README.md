# CoordinatorStorytelling-BE
Coordinator Storytelling Backend


# All Get Enpoints are fully functional and deployed to the http://coordinator-storytelling.herokuapp.com/ server

the GET Endpoints are :
 
/stories/all     (no authentication) - returns list of all stories from all coordinators

/stories/5      (no authentication) - returns the story associated with id ("5" in this case)

/stories/admin (no authentication) - returns all stories associated with a user "admin"

/stories/mine (requires token) - returns all stories from whomever is logged in

/stories/random (no authentication) - returns random story from a random coordinator


the currently working POST Endpoints are:

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
