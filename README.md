# Auto Picture Gallery Maker
Automatically scans a directory for images and creates a picture gallery of HTML web pages within.
Double click AutoPictureGalleryMaker.jar to generate one within the "bucket" folder.

This was developed in VisualStudio Code so I strongly recommend following this tutorial to get set up if you want to play around with my code:
https://www.youtube.com/watch?v=zyyDcfQYwIw

# How To Use
1. Place the outside the folder where you want to generate the gallery.
- AutoPictureGalleryMaker.jar
- parameters.txt
- The folder called: templates.

2. In parameters.txt replace on the line that starts with "Work in Folder" after the colon: put the exact name of the folder containing your image folders. There should not be a space between the colon and the folder name. Remember to save. 

Bear in mind that the program will generate a separate gallery for every folder within the root folder it finds. For example, if the folder you indicated contains folders called "vacation","dogs","food" it will generate 3 galleries - one for each folder.

You may also change the page size if you like. This changes the number of images per page.

3. Double click AutoPictureGalleryMaker.jar to generate the gallery.

4. Once it finishes put the CSS folder into your gallery folder.

You should now be able to run any one of the html folders to open the gallery.
