# Auto Picture Gallery Maker
Automatically scans a directory for images and creates a picture gallery of HTML web pages within.
Double click AutoPictureGalleryMaker.jar to generate one within the "bucket" folder.

YouTube Demo: https://www.youtube.com/watch?v=VyXjz1QjIkk

This was developed in VisualStudio Code so I strongly recommend following this tutorial to get set up if you want to play around with my code:
https://www.youtube.com/watch?v=zyyDcfQYwIw

# Parameters Meaning (within parameters.txt)
Page Size = How many images per web page.

Navigation Bar Size = Maximum number of links allowed on the navigation bars.

Work in Folder = The folder that contains the image folders and where the gallery files will be output.

Separate Gallery for Each Folder = If false (default), it will create 1 gallery for every folder within the work in folder. If true it will create a separate gallery for every folder in the root directory.

Default Gallery Name: The name of the gallery files if Separate Gallery for Each Folder is set to false.

# How To Use
1. Place the outside the folder where you want to generate the gallery.
- AutoPictureGalleryMaker.jar
- parameters.txt
- The folder called: templates.

2. In parameters.txt replace on the line that starts with "Work in Folder" after the colon: put the exact name of the folder containing your image folders. There should not be a space between the colon and the folder name. Remember to save. 

You may edit the other parameters to your liking using the Parameters Meaning (within parameters.txt) section.

3. Double click AutoPictureGalleryMaker.jar to generate the gallery.

4. Once it finishes put the CSS folder into your gallery folder.

You should now be able to run any one of the html folders to open the gallery.
