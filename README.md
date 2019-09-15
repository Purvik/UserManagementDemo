# UserManagementDemo
Simple Android app to demonstrate user management using Firebase.

##activities/Login
Login using email and password is implemented here. At the time of login it'll check weather email is verified or not.

##activities/Registration
Register user uing email and password. Upon successful registration, an activaion link will be sent to the email for verification.

##activities/ForgotPassword
From here, user can reset new password from the link sent to their provided email id.

##activities/UserInformation
Take other required details from user to store it on Firebase real time database.

##entities/User
A user class holding required parameters (properties) of an app user.

##utils/AppHelpers
Seperate class to check validation and other stuffs.
