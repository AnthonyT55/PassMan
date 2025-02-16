Simple CLI password manager created in Java. Currently still working on a few bugs but base functionality is complete. The way this program works is: 
1.)Asks the user to provide a username, a key to load their information, and then a decryption key
2.)The program then stores the provided authentication details to a Java Keystore, then creates and encrypts a password file using the given data
3.)After authentication (only if gone correctly), the user will then be greeted with the PassMan menu, which then allows users to use the many features of PassMan.

The current options for PassMan are as follows: 
add credentials, show credentials, delete credentials, and generate password, as well as exit of course.
Upon exiting the program, the user's files will be encrypted and the decrypted file is deleted.
