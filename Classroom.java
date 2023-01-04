package kindergarten;

import java.util.LinkedList;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    
     public void makeClassroom ( String filename ) {
        
        //WRITE YOUR CODE HERE
        
        StdIn.setFile(filename);
        int x = StdIn.readInt();                                        //read the number of students
        studentsInLine = null; 
        
        for (int i = 0; i < x; i++){
            String firstName = StdIn.readString();                      //read the first name
            String lastName = StdIn.readString();                       //read the last name
            int height = StdIn.readInt();                               //read the height

            Student newStudent = new Student(firstName,lastName,height);//store in Student Object
            SNode ptr = studentsInLine;
            //System.out.println(newStudent);
            
            if (ptr == null){                                           //if the head is null
                studentsInLine = new SNode(newStudent,null);         //if there is only one student then the line just ends there, thus it will be null
                ptr = studentsInLine;
            }
            else{                                                      //do comparison and insertion
                
                if (ptr.getStudent().compareNameTo(newStudent) > 0) {
                    studentsInLine = new SNode(newStudent, ptr);       //the ptr will have the new student
                    ptr = studentsInLine;                              //ptr remains in the first node
                }
                else{
                    SNode movePtr = ptr;
                    while (movePtr.getNext() != null && movePtr.getNext().getStudent().compareNameTo(newStudent) < 0){ //get the next pointer and student and compare with the current one
                       movePtr = movePtr.getNext();                                                                    //update the movePtr
                    }
                    SNode orderName = new SNode (newStudent, movePtr.getNext());
                    movePtr.setNext(orderName);
                } 
            }  
        }    
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

	    // WRITE YOUR CODE HERE
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();                    //rows
        int c = StdIn.readInt();                    //cols
        seatingAvailability = new boolean [r][c];   //to store the boolean    
        studentsSitting = new Student[r][c];

        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () { 

	    // WRITE YOUR CODE HERE        
        
         boolean winneradded = false;

         if (musicalChairs != null){
            for (int i = 0; i < studentsSitting.length; i++){
                for (int j = 0; j < studentsSitting[i].length; j++){
                    if (seatingAvailability[i][j] == true && studentsSitting[i][j] == null ){
                        studentsSitting[i][j] = musicalChairs.getStudent();
                        winneradded = true;
                        break;  
                    }  
                }
                if (winneradded){
                    break;
                }
            }
        }
        
        for (int i = 0; i < studentsSitting.length; i++){
            for (int j = 0; j < studentsSitting[i].length; j++){
                if (seatingAvailability[i][j] == true && studentsSitting[i][j] == null ){       //if there are students in the seating chart
                    if (studentsInLine != null){                                                   //there are students in line
                        studentsSitting[i][j] = studentsInLine.getStudent();                       //get the student sitting from the line of students
                        studentsInLine = studentsInLine.getNext();                                    //get the next student
                    }
                    else{                                                                       //musical chairs empty, return 
                        return;
                    }
                }
            }
        }
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {     
         
        // WRITE YOUR CODE HERE

        SNode last = null;                                         //initialize last student to null
        SNode first = null;                                        //initialize first student to null

        for (int i = 0; i < studentsSitting.length; i++){          //travers through the studentsSitting
            for (int j = 0; j < studentsSitting[i].length; j++){
                
                if (studentsSitting[i][j] != null){                 //if student sitting. never be null as there is always something connected 
                    Student studentSitting = studentsSitting[i][j]; //the current student added to the object
                    if (musicalChairs == null){
                        SNode lastptr = new SNode(studentSitting, null);
                        lastptr.setNext(lastptr);
                        musicalChairs = lastptr;
                    }
                    else{
                        SNode newStudent = new SNode(studentSitting, null);
                        newStudent.setNext(musicalChairs);
                        musicalChairs.setNext(newStudent);
                        musicalChairs = newStudent;                 //the newStudent is the last one in the CLL 

                    }
                    SNode addStudentLast = new SNode(studentSitting, null);
                    studentsSitting[i][j] = null;
                    
                    if (first == null){
                        first = addStudentLast; 
                        last = first;
                        addStudentLast.setNext(first);              //the front
                    }else{
                        last.setNext(addStudentLast);               //add to the last
                        last = addStudentLast;
                        last.setNext(first);
                    }
                }
            }   
        }
        musicalChairs = last;
    }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {  //CHECK

        // WRITE YOUR CODE HERE

        //printStudentsInLine();
        //printMusicalChairs();
        int size = 1;
        SNode ptr = musicalChairs;                                                       //reference to the last node of CLL

        //iterate through the musical chair students to find the size
        for (SNode i = ptr.getNext(); i != musicalChairs; i = i.getNext()){             //ptr refer to the last node; ptr.getnext refer to the first node now
            size++;                                                          
        }

        while(size > 1){
            int randStudent = StdRandom.uniform(size);                                  //generate a random number //size or size-1
            SNode ptrStudent = new SNode();
            ptrStudent= getMusicalChairs().getNext();                                   //first node in CLL
            SNode prevStudent = getMusicalChairs();                                     //last node in CLL
            while (randStudent > 0){
                prevStudent = ptrStudent;
                ptrStudent = ptrStudent.getNext(); 
                randStudent--;            
            }

           // printMusicalChairs();
           
            if (ptrStudent.getStudent() == musicalChairs.getStudent()){                  //if it is the last node in CLL
                prevStudent.setNext(musicalChairs.getNext());
                musicalChairs = prevStudent;
                
            }

            else if (ptrStudent.getStudent() == musicalChairs.getNext().getStudent()){  //if first node in CLL 
                prevStudent.setNext(ptrStudent.getNext());                              //prev is the last node, ptr is the first node
            }
            else {                                                                      //if middle node
                prevStudent.setNext(ptrStudent.getNext()); 
            }
            //System.out.println(ptrStudent.getStudent());
            //printMusicalChairs();

            size --;

            //insert
            //System.out.println(ptrStudent.getStudent().getFullName());
            if (studentsInLine == null){                                                //if the LL is empty
                SNode temp = new SNode(ptrStudent.getStudent(), null);
                studentsInLine = temp;
            }
            else{
                SNode ptrStudent2 = studentsInLine.getNext();                           //second node in LL
                SNode prevStudent2 = studentsInLine;                                    //first node in LL
                SNode temp = new SNode (ptrStudent.getStudent(), null);

                if (temp.getStudent().getHeight() < studentsInLine.getStudent().getHeight()){
                    temp.setNext(studentsInLine);
                    studentsInLine = temp;
                }

                else{                    
                    while (temp.getStudent().getHeight() > ptrStudent2.getStudent().getHeight()){
                        prevStudent2 = ptrStudent2;
                        ptrStudent2 = ptrStudent2.getNext();
                    }
    
                    prevStudent2.setNext(temp);
                    temp.setNext(ptrStudent2);
                }
            }        
        //printStudentsInLine();
        }
        seatStudents();
        musicalChairs = null;
    } 

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {//CHECK
        // WRITE YOUR CODE HERE
        Student lateStudent = new Student(firstName, lastName,height);
        SNode newStudent = new SNode(lateStudent, null);
        if (studentsInLine != null){                        //add in student in line
            SNode currentStudent1 = getStudentsInLine();    //points to the last student in line
            SNode currentStudent2 = null;
            while(currentStudent1 != null){
                currentStudent2 = currentStudent1;
                currentStudent1 = currentStudent1.getNext();  
                
            }
            currentStudent2.setNext(newStudent);
            
        }
        if (musicalChairs != null){                          //add in musical chairs
            SNode currentStudent3 = musicalChairs;
            SNode newStudent2 = new SNode(new Student(firstName,lastName,height), currentStudent3.getNext());
            currentStudent3.setNext(newStudent2);
            musicalChairs = newStudent2;
        }
        else{                                               //add in seat students
            for (int i = 0; i < studentsSitting.length; i++){  
                for (int j = 0; j < studentsSitting[0].length; j++){   
                    if (studentsSitting[i][j] == null && seatingAvailability[i][j] == true){
                        studentsSitting[i][j] = newStudent.getStudent();
                        return;
                    }
                }
            }
        }   
    }


    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) { //CHECK THIS

        // WRITE YOUR CODE HERE
        if (getStudentsInLine() != null){                                                   //Delete from students in line LL
            SNode currentStudent = studentsInLine;
            SNode oldStudent = new SNode();
            
            if (currentStudent.getStudent().getFirstName().compareTo(firstName) == 0 && 
            currentStudent.getStudent().getLastName().compareTo(lastName) == 0){            //Delete first name in LL
                studentsInLine = getStudentsInLine().getNext();

            }
            while (currentStudent.getStudent().getFirstName().equalsIgnoreCase(firstName) != true &&  //ignore case- search for first and last name abbreviation
                currentStudent.getStudent().getLastName().equalsIgnoreCase(lastName) != true){
                    oldStudent = currentStudent;
                    currentStudent = currentStudent.getNext();
            }
            oldStudent.setNext(currentStudent.getNext());
        }
        if (getMusicalChairs() != null){                                                    //Delete from musical chairs CLL
            SNode currentStudent = musicalChairs;
            SNode oldStudent = new SNode();
            
            if (currentStudent.getStudent() == getMusicalChairs().getNext().getStudent()){ //Delete first student in the CLL 
                SNode front = new SNode();
                SNode last = new SNode();
                front = getMusicalChairs().getNext();
                last = getMusicalChairs();
                front = last;
                last = front;
            }

            if (getMusicalChairs().getStudent().getFirstName().compareTo(firstName) == 0
            && getMusicalChairs().getStudent().getLastName().compareTo(lastName) == 0) { //CHECK---delete last student in CLL
                SNode front = getMusicalChairs().getNext(); //first node
                SNode last = getMusicalChairs(); //last node
                SNode prev = new SNode();
                while (front != last){
                    prev = front; //one node behind front
                    front = front.getNext();
                }
                if (front == last){
                    prev = last;
                    last = prev;
                }
            }
            while (currentStudent.getStudent().getFirstName().equalsIgnoreCase(firstName) != true &&  //ignore case- search for first and last name abbreviation
                currentStudent.getStudent().getLastName().equalsIgnoreCase(lastName) != true){
                    oldStudent = currentStudent;
                    currentStudent = currentStudent.getNext();
            }
            oldStudent.setNext(currentStudent.getNext());
        }
        else{                                                                           //Delete from students seating
            for (int i = 0; i < studentsSitting.length; i++){
                for (int j = 0; j < studentsSitting[0].length; j++){   
                    if (studentsSitting[i][j] != null){
                        if (studentsSitting[i][j].getFirstName().equalsIgnoreCase(firstName) && 
                            studentsSitting[i][j].getLastName().equalsIgnoreCase(lastName)){
                            studentsSitting[i][j] = null;
                        }
                    }
                }
            }
        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
