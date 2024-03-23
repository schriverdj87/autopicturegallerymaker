//Created by David Schriver - March 2024

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;



//Resources
//https://www.youtube.com/watch?v=zyyDcfQYwIw <-- This is a must if you don't want to use IntelliJ.
//https://kotlinandroid.org/kotlin/kotlin-check-if-file-exists/
//https://www.baeldung.com/kotlin/write-file
//https://www.baeldung.com/kotlin/read-file
//https://www.baeldung.com/kotlin/create-directory
//https://stackoverflow.com/questions/49419971/kotlin-get-list-of-all-files-in-resource-folder
//https://www.techiedelight.com/add-new-element-array-kotlin



var pageSize: Int = 20;

val paramPath = "./parameters.txt"; //Location of the parameters

//Html template locations
val htmltemplatePath = "./templates/htmltemplate.html";
val imgtemplatePath = "./templates/imgtemplate.html";
val anctemplatePath = "./templates/htmltemplateanc.html";
val spantemplatePath = "./templates/htmltemplatespan.html";

//String patterns within the templates that the system replaces
var htmltemplateKey = "[IMGSGOHERE]";
var htmltemplatenavKey = "[NAVGOESHERE]"
var imgtemplateKey = "[PATHHERE]"
var numKey = "[NUMHERE]"

//There the code stores the html templates
var htmlString = "";
var imgString = "";
var spanString = "";
var ancString = "";

var bucketPath = "./bucket";
val acceptableExtensions = arrayOf("png","jpg","jpeg","gif","bmp","webp","tif","svg");

//Parameter names and defaults
val paramStrPageSize = "Page Size";
var paramStrBucketFolder = "Work in Folder"
val paramDefault = paramStrPageSize + " :" + pageSize.toString() + "\n" + paramStrBucketFolder + " :" + bucketPath.replace("./","");

var logOutput = "";

//Gets the contents of a file as a string.
fun GetFileAsString (path:String):String
{
    var toSend:String = "";
    var theFile:File = File(path);

    if (theFile.exists() == false)
    {
        AddToLog("GetFileAsString says : \"" + path + " does not exist, fartknocker!.\"");
        return toSend;
    }

    theFile.forEachLine {toSend = toSend + it + "\n"}


    return toSend;
}

fun GetExtensionFromPath(path:String):String
{
    var splitpath = path.split(".");

    return (splitpath[splitpath.size - 1].lowercase());
    
}


fun AddToLog(toadd:String)
{
    logOutput = logOutput + toadd + "\n";
}


fun TrimPath(totrim:String, levels:Int = 1):String
{
    var totrimB = totrim.replace("./","").split("/");
    var levelsRelay = levels;
    var toSend = "";

    for(lePath in totrimB)
    {
        if (levelsRelay > 0)
        {
            levelsRelay--
        }
        else
        {
            toSend = toSend + lePath;

            if (lePath != totrimB[totrimB.size - 1])
            {
                toSend = toSend + "/";
            }
        }
    }

    return toSend;

}

fun main (args:Array<String>)
{


//Look for and load the variables from the parameter file. Includes: Page size. If it doesn't exist, make it.
AddToLog("Looking for " + paramPath);
var fileParams:File = File(paramPath);

if (File(paramPath).exists() == false)
{
    AddToLog ("Not found. Creating...");
    fileParams.writeText(paramDefault);
}
else
{
    AddToLog ("Found!");
    
    var rawParams = GetFileAsString(paramPath).split("\n");

    for (ln in rawParams)
    {
        if (ln.indexOf(paramStrPageSize) == 0)
        {
            AddToLog("Page Size Found!");
            pageSize = ln.split(":")[1].replace(" ","").toInt();
            AddToLog("Page Size set to $pageSize.");
        }
        if (ln.indexOf(paramStrBucketFolder) == 0)
        {
            AddToLog("Working Path Found!");
            bucketPath = "./" + ln.split(":")[1];
            AddToLog("Working Path Set to $bucketPath");
            
        }
    }

}

//Look for and load the 2 template files. Shut down if these are missing.
AddToLog("Looking for template files")


if (File(htmltemplatePath).exists() == false || File(imgtemplatePath).exists() == false || File(anctemplatePath).exists() == false || File(spantemplatePath).exists() == false )
{
    if (File(htmltemplatePath).exists() == false)
    {
        AddToLog("ERROR: $htmltemplatePath is missing. Please create that file.");
    }

    if (File(imgtemplatePath).exists() == false)
    {
        AddToLog("ERROR: $imgtemplatePath is missing. Please create that file.");
    }

    if (File(anctemplatePath).exists() == false)
    {
        AddToLog("ERROR: $anctemplatePath is missing. Please create that file.");
    }

     if (File(spantemplatePath).exists() == false)
    {
        AddToLog("ERROR: $spantemplatePath is missing. Please create that file.");
    }
}
else
{
AddToLog("All found.")

htmlString = GetFileAsString(htmltemplatePath);
imgString = GetFileAsString(imgtemplatePath);
ancString = GetFileAsString(anctemplatePath);
spanString = GetFileAsString(spantemplatePath);

//Look for the bucket folder, if it does not exist make it.
AddToLog("looking for working folder.")

if (File(bucketPath).exists() == false)
{
    AddToLog("Working folder does not exist. Creating...");
    Files.createDirectory(Paths.get(bucketPath));
    
}
//Look in the bucket folder for 1 level of sub-folders, keep this somewhere.
var bucketFolder = File(bucketPath);
var bucketFolders = arrayOf<String>();


AddToLog("Getting top-level folders")
bucketFolder.walk().forEach{ lePath -> 
var lePathString = lePath.toString();
if (lePathString.length - lePathString.replace("\\","").length == 2 && lePath.isFile == false)//Only the first level folders.
{
    bucketFolders += lePathString;
}

}

//FOR EACH sub-folder
for (lePath in bucketFolders)
{
    //Locate every image file path, wrap it in an HTML image element and add it to a flat string within an array of strings. 
    //When the count hits the page file, add a new one. Edit the latest string in the array.    

    AddToLog("Getting images for $lePath");
    var imgCount = 0;
    var imgList = arrayOf("");
    val leFolder = File(lePath);
    var hadFiles = false;

    leFolder.walk().forEach {lePath -> 
        //Start a new page if the current one gets "full"
        if (imgCount == pageSize)
        {
            imgCount = 0;
            imgList += "";
        }

        //If it's an image file, process it.
        if (lePath.isFile() && GetExtensionFromPath(lePath.toString()) in acceptableExtensions)
        {
            
            var justBucketFolderName = bucketPath.replace("./","");//Flipping slashes...
            
            var snippedPath = lePath.toString().replace(".\\$justBucketFolderName\\","");//Clear away the "bucket" path.

            AddToLog ( "Processing: $snippedPath" );


            imgList[imgList.size - 1] = imgList[imgList.size - 1] + (imgString.replace(imgtemplateKey,snippedPath));

            hadFiles = true;
            imgCount++;
        }

        

    }

    //FOR EACH STRING IN THE ARRAY IN THE PREVIOUS STEP
    //Wrap it in the bigger template ane export as an HTML file with a similar name to the folder.

    AddToLog("Pages:" + imgList.size.toString() );
    imgCount = 1; //Reusing this for the page name.
    
    if (hadFiles)//Don't bother with folders without images (such as the css folder)
    {
    for (page in imgList)
    {
        //Generating the nav bar
        var navComponent = "";

        var navCountDown = imgList.size;

        while (navCountDown > 0)
        {
            var templateToUse = ancString;

            if (navCountDown == imgCount)//Don't bother turning the current number into a link
            {
                templateToUse = spanString;
            }

            var endFileName = lePath.split("\\")[lePath.split("\\").size-1];

            navComponent = templateToUse.replace(numKey,navCountDown.toString()).replace(imgtemplateKey,(endFileName + navCountDown.toString() + ".html")) + navComponent;

            navCountDown--;
        }

        //Assembling the page
        var toSend = htmlString.replace(htmltemplateKey,page).replace(htmltemplatenavKey,navComponent);
        var outPath = lePath + imgCount.toString() + ".html";

        AddToLog("Writing " + outPath);

        //And shipping it out.
        File(outPath).writeText(toSend);
        imgCount++;
    }
}

    
}

}

//Send the log file
File("./log.txt").writeText(logOutput);


    
}