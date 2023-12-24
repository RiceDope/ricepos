# ERRORS

This is a list of known errors that can occur and ways that they can be resolved.

## *IMPORTANT*
For errors A1 and A2. It may be easier to delete the files and remake them as there is no tracking for removed IDs and finding the IDs that have been removed and not repopulated may be harder.

## A1
This is an error in the FileMangagement script. The default stock is attempting to be written to however it appears that StockID is not 0. This means that the stock file has gone missing and is being regenerated. This means no stock will be available in the system. If you have deleted or removed the stock.json file put it back in the correct directory and then change sysfiles.json to the correct stockID *One higher than the last stock added*

A new default stock.json file will be created however this can be replaced with your given stock file.

*STOCK ID WILL ALSO BE INCREMENTED SO MAKE SURE TO SET CORRECTLY*

## A2
This is an error in the FileManagement script. The stock.json file exists but the sysfiles.json does not. This means that there could be stock that will be overwritten should the StockID value not be corrected. Please open the sysfiles.json and update the StockID field to match one higher than your current highest StockID in stock.

A new sysfiles.json file will be created regardless.

### A2 Edge case *KNOWN*
There is an edge case where if stock exists but has nothing other than default stock then the program will assume that there is an issue. However there isnt we can just create sysfiles and move forward. However it will not be incremented correctly so we just force an increment in this case. However you may still get the A2 error message. This cannot be preveneted so in the case that you check your files and it seems fine it probably is. Relaunch the program and see if there is another error.

## A1, A2
The issue here is that one file exists without the other and they depend on eachother. Check the individual error you get to make sure that they are dealt with correctly.

If any issues persist with these two files just remove them and start again.