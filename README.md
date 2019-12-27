# address-assignment prompt

Initially a coding challenge assigned to be completed as "homework" this project serves as a display of my
skills creating a command line app using Clojure. The prompt that was given to me is as follows:

>" **Step 1 - Build a system to parse and sort a set of records** 
>
> Create a command line app that takes as input a file with a set of records in one of three formats
 described below, and outputs (to the screen) the set of records sorted in one of three ways.  
>
> **Input** 
>
 >A record consists of the following 5 fields: last name, first name, gender, date of birth and favorite
 color. The input is 3 files, each containing records stored in a different format. You may generate
 these files yourself, and you can make certain assumptions if it makes solving your problem easier.  
>
 > The pipe-delimited file lists each record as follows:  
> `LastName | FirstName | Gender | FavoriteColor | DateOfBirth`  
>
 > The comma-delimited file looks like this:   
 `LastName, FirstName, Gender, FavoriteColor, DateOfBirth` 
 
 > The space-delimited file looks like this: 
 `LastName FirstName Gender FavoriteColor DateOfBirth`
 
 > You may assume that the delimiters (commas, pipes and spaces) do not appear anywhere in the
 data values themselves. Write a program in a language of your choice to read in records from these
 files and combine them into a single set of records. 
> 
 **Output**
 > Create and display 3 different views of the data you read in:
 - Output 1 – sorted by gender (females before males) then by last name ascending.
 - Output 2 – sorted by birth date, ascending.
 - Output 3 – sorted by last name, descending.
 > Display dates in the format M/D/YYYY.
>
>
 > **Step 2 - Build a REST API to access your system** 
>
 > Tests for this section are required as well.
 Within the same code base, build a standalone REST API with the following endpoints:
 - POST /records - Post a single data line in any of the 3 formats supported by your existing code
 - GET /records/gender - returns records sorted by gender
 - GET /records/birthdate - returns records sorted by birthdate
 - GET /records/name - returns records sorted by name
 > It's your choice how you render the output from these endpoints as long as it well structured data.
 These endpoints should return JSON.
 To keep it simple, don't worry about using a persistent datastore. "

## Usage

As command line:
```bash 

# no sorting
clj -m address-assignment.core f1 f2 ... fn

# sort by gender then by last name
clj -m address-assignment.core -s genderlast f1 f2 ... fn

# sort by dob
clj -m address-assignment.core -s dob f1 f2 ... fn

# sort by last name
clj -m address-assignment.core -s last f1 f2 ... fn
 ```

As server:

```bash 
clj -m address-assignment.server 8000

# sorted by gender
curl http://localhost:8000/records/gender

# sorted by birthdate
curl http://localhost:8000/records/birthdate

# sorted by lastname
curl http://localhost:8000/records/name

# parse record line
curl -X POST -H "Content-Type: application/json" -d '{"data": "Canto,Susann,Female,Teal,5/8/2019"}' http://localhost:8000/records
```

Testing:
```bash 
clj -A:runner
```

## Assumptions
For testing purposes I created sample data using [Mockaroo](https://mockaroo.com/).
In the context of this assignment, the assumption has been made that tabs are equivalent
to spaces since spaces are not offered as a delimiter option on Mockaroo.

*Note regarding spec* **Data that included superfluous whitespace (such as: "Linda Van Bullwinkle") has been deleted
for testing purposes. I am operating on the assumption that the data going in is clean, uniform data.**   

## License


This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
