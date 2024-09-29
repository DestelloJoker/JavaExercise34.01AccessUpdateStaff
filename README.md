# JavaExercise34.01AccessUpdateStaff

This program uses both javaFX and mySQL to make a program that connects this GUI to a SQL database by changing the final Strings of DB_URL, DB_USER, and DB_PASSWORD, and in the GUI what you input in the input boxes. 
You have the choice to insert it directly into the mySQL database at the ID provided since theID is the primary key, update the data at the ID, or view the specific data with the ID provided.

You must have Intellij or some IDE that is configured to run both mySQL, and javaFX; If you don't the program will fail to work properly, and you need to modify a couple of fields as well, those being DB_URL, DB_USER, and DB_PASSWORD.
Then lines 111, 136, and 157 they contain exercise34_01.userdata(schema_name.table_name) inside those lines and that is just the schema.table, configure those so the program properly works when running it yourself.
