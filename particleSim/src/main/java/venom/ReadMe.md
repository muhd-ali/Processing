Run the following command to register processing core jar first

    mvn install:install-file -Dfile="C:\ProgramData\chocolatey\lib\Processing\tools\processing-3.5.4\core\library\core.jar" -DgroupId="com.processing" -DartifactId=core -Dversion="3.5.4" -Dpackaging=jar -DgeneratePom=true -DupdateReleaseInfo=true
