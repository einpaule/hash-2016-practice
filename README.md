# hash-2016-practice
made for the google hash challenge

# How to open in Eclipse:

You should have the Maven plugin and the Testng plugin installed. When you have this, do the following:

* go to the folder you want the project to live in
* clone the repository: `git clone git@github.com:einpaule/hash-2016-practice.git`
* go into eclipse and select import *Existing project into Workspace* and follow the steps
* right click the project that you have imported and select *Maven > Update Project*


# To compile and run from command line:

* run `mvn clean compile assembly:single` from your project's folder
* run the jar in the target folder by running `java -jar your_jar_file_name.jar target-file-name.txt`
