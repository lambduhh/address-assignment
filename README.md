# address-assignment

An assignment assigned to be completed at my address.. You could call it '*homework*'

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

# sorted by gedner
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
