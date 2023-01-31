/*
File Name: StudentProject.java

Developed by : P K Subudhi
Contact me on (for any help): 
	WhatsApp: +91-8895174939
	Email: mailtopksubudhi
	Website: www.pksubudhi.com

DESCRIPTION:

This is the start-up source code file. It does the following when we start the program
	1. Creates a linked list based database based on the source contents in the specified database file (here it is SchoolDB_Initial.txt)
		NOTE: The database file is a simple flat file, in order to avoid confusion I have used a .txt file created on Notepad. You can also 
		make one for you and can use that eaither.
	2. Displays Menu System
		It has 13 menu options (all text based) for various operations

		MAIN MENU
		*******************

		1. Create/Add 3 Course
		2. Create/Add 3 Faculty
		3. Create/Add 3 General Staff
		4. Create/Add 3 Student
		5. Add two Course to a Faculty
		6. Add two Courses to Student
		7. Add array of two Course to a Faculty
		8. Add array of two Courses to Student
		9. Get course at index of a Faculty
		10. Get Course at index of a Student
		11. Check Course with Faculty
		12. Faculty With Most and Least Course
		13. Exit
*/
import java.lang.*;
import java.util.*;
import java.io.*;

public class StudentProject {
	static Course courseList = null;
	static Student studentList = null;
	static Faculty facultyList = null;
	static GeneralStaff staffList = null;
	public static void main(String args[]) {
		

		Course newCourse;
		Student newStudent = null;
		Faculty newFaculty = null;
		GeneralStaff newStaff=null;

		String str;
		int i=0;
		String words[];
		try {
			// Reading from file
			File file = new File("SchoolDB_Initial.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((str=br.readLine())!=null) {
				
				//Displaying input content read from file
				System.out.println(str);

				//Creating respective objects
				if(str.length()>0) {
				words = str.split("\\s");
				switch(words[0]) {
					case "Course:":
						str = str.substring(words[0].length());
						if (str.length() > 0)
							words = str.split(",");
						else
							words=null;
						if (words.length==4) {
						newCourse = new Course(Boolean.parseBoolean(words[0]),Integer.parseInt(words[1]),words[2],Integer.parseInt(words[3]));
						courseList = addToCourseList(courseList, newCourse);
						}
						else {
							System.out.println("Invalid/insufficient data to create Course Object");
						}
						break;
					case "Student:":
						str = str.substring(words[0].length());
						if (str.length() > 0) {
							words = str.split(",");
							
							if (words.length == 1) {
								newStudent = new Student(Boolean.parseBoolean(words[0]));
							}
							if (words.length == 2) {
								newStudent = new Student(words[0], Boolean.parseBoolean(words[1]));
							}
							if (words.length == 4) {
								newStudent = new Student(words[0], Integer.parseInt(words[1]), words[2], Boolean.parseBoolean(words[3]));
							}
						}
						else {
							
							newStudent = new Student();
							
						}
						studentList = addToStudentList(studentList, newStudent);
						break;
					case "Faculty:":
						
						str = str.substring(words[0].length());
						if (str.length() > 0) {
							words = str.split(",");
							if (words.length == 1) {
								newFaculty = new Faculty(Boolean.parseBoolean(words[0]));
							}
							if (words.length == 2) {
								newFaculty = new Faculty(words[0], Boolean.parseBoolean(words[1]));
							}
							if (words.length == 4) {
							newFaculty = new Faculty(words[0], Integer.parseInt(words[1]), words[2], Boolean.parseBoolean(words[3]));
							}
						}
						else {
							newFaculty = new Faculty();
						}
						facultyList = addToFacultyList(facultyList, newFaculty);
						break;
					case "GeneralStaff:":
						
						str = str.substring(words[0].length());
						if (str.length() > 0) {
							words = str.split(",");
							if (words.length == 1) {
								newStaff = new GeneralStaff(words[0]);
							}
							if (words.length == 2) {
								newStaff = new GeneralStaff(words[0], words[1]);
							}
							if (words.length == 4) {
							newStaff = new GeneralStaff(words[0], Integer.parseInt(words[1]), words[2], words[3]);
							}
						}
						else {
							newStaff = new GeneralStaff();
						}
						staffList = addToStaffList(staffList, newStaff);
						break;
				}	//End of switch
				}	// End if if block
			}
		}
		catch(IOException ex) {
			System.out.println("Exception: "+ex.toString());
		}

		// Displaying all object contents that are created from the input file
		System.out.println("\n\n*****Displaying Data Created with List of Objects*****\n\n");
		System.out.println("\n+==========================================================+");
		System.out.println("| Courses                                                  |");
		System.out.println("+==========================================================+");
		dispCourseList(courseList);
		System.out.println("\n+==========================================================+");
		System.out.println("| Students                                                 |");
		System.out.println("+==========================================================+");
		dispStudentList(studentList);
		System.out.println("\n+==========================================================+");
		System.out.println("| Faculties                                                |");
		System.out.println("+==========================================================+");
		dispFacultyList(facultyList);
		System.out.println("\n+==========================================================+");
		System.out.println("| General Staffs                                           |");
		System.out.println("+==========================================================+");
		dispStaffList(staffList);
		
		//Menu driven operations
		menu();

		//Displaying updated Data
		System.out.println("\n\n*****Displaying Updated Database*****\n\n");
		System.out.println("\n+==========================================================+");
		System.out.println("| Courses                                                  |");
		System.out.println("+==========================================================+");
		dispCourseList(courseList);
		System.out.println("\n+==========================================================+");
		System.out.println("| Faculties                                                |");
		System.out.println("+==========================================================+");
		dispFacultyList(facultyList);
		System.out.println("\n+==========================================================+");
		System.out.println("| General Staffs                                           |");
		System.out.println("+==========================================================+");
		dispStaffList(staffList);
		System.out.println("\n+==========================================================+");
		System.out.println("| Students                                                 |");
		System.out.println("+==========================================================+");
		dispStudentList(studentList);

		//Writing all contents to a file
		saveToFile();
	}
	public static void menu() {
		int choice;
		Scanner kb = new Scanner(System.in);		// To get input from Keyboard
		int count = 0;
		Boolean isGrad;
		String cDept;
		int cNum, credits;
		
		String fName, fDept;
		Boolean tenured;
		int bYear;
		String duty;
		String major;
		
		// array of two course objects
		Course[] courses = new Course[2];
		courses[0] = new Course(true, 625, "CMP", 6);
		courses[1] = new Course(false, 776, "MAT", 5);

		int sID, fID, courseCount, cIndex;
	while(true) {
		System.out.println("\n\nMAIN MENU");
		System.out.println("*******************\n");
		System.out.println("1. Create/Add 3 Course");
		System.out.println("2. Create/Add 3 Faculty");
		System.out.println("3. Create/Add 3 General Staff");
		System.out.println("4. Create/Add 3 Student");
		System.out.println("5. Add two Course to a Faculty");
		System.out.println("6. Add two Courses to Student");
		System.out.println("7. Add array of two Course to a Faculty");
		System.out.println("8. Add array of two Courses to Student");
		System.out.println("9. Get course at index of a Faculty");
		System.out.println("10. Get Course at index of a Student");
		System.out.println("11. Check Course with Faculty");
		System.out.println("12. Faculty With Most and Least Course");
		System.out.println("13. Exit");
		System.out.print("\nEnter your choice: ");
		choice = kb.nextInt();
		switch(choice) {
			case 1:
				System.out.println("**** Creating 3 Course Objects ***");
				for(count=1; count<=3; count++) {

					System.out.println("\nInput for Course Object #"+count);
					System.out.print("Is the new course a Graduate course? true/false: ");
					isGrad = kb.nextBoolean();
					System.out.print("Enter Course Number: ");	
					cNum = kb.nextInt();
					kb.nextLine();
					System.out.print("Enter Course Department [Example MAT, CSE]: ");
					cDept = kb.nextLine();
					System.out.print("Enter Credits: ");
					credits = kb.nextInt();
					
					courseList = addToCourseList(courseList, new Course(isGrad, cNum, cDept, credits));
				
				}
				break;
			case 2:
				System.out.println("**** Creating 3 Faculty Objects ***");
				for(count=1; count<=3; count++) {

					System.out.println("\nInput for Faculty Object #"+count);
					
					
					System.out.print("Enter Birth year: ");	
					bYear = kb.nextInt();
					kb.nextLine();
					System.out.print("Enter Name: ");
					fName = kb.nextLine();
					System.out.print("Enter Department [Example MAT, PHY]: ");
					fDept = kb.nextLine();
					System.out.print("Is the faculty tenured? [true/false]: ");
					tenured = kb.nextBoolean();
					
					facultyList = addToFacultyList(facultyList, new Faculty(fName, bYear, fDept, tenured));
				
				}
				break;
			case 3:
				System.out.println("**** Creating 3 GeneralStaff Objects ***");
				for(count=1; count<=3; count++) {

					System.out.println("\nInput for GeneralStaff Object #"+count);
					
					
					System.out.print("Enter Birth year: ");	
					bYear = kb.nextInt();
					kb.nextLine();
					System.out.print("Enter Name: ");
					fName = kb.nextLine();
					System.out.print("Enter Department [Example security, sanitation]: ");
					fDept = kb.nextLine();
					System.out.print("Entre Duty Description: ");
					duty = kb.nextLine();
					
					staffList = addToStaffList(staffList, new GeneralStaff(fName, bYear, fDept, duty));
				
				}
				break;
			case 4:
				System.out.println("**** Creating 3 Student Objects ***");
				for(count=1; count<=3; count++) {

					System.out.println("\nInput for Student Object #"+count);
					
					
					System.out.print("Enter Birth year: ");	
					bYear = kb.nextInt();
					kb.nextLine();
					System.out.print("Enter Name: ");
					fName = kb.nextLine();
					System.out.print("Enter Major: ");
					major = kb.nextLine();
					System.out.print("Is a Graduate Student? [true/false]: ");
					isGrad = kb.nextBoolean();
					
					studentList = addToStudentList(studentList, new Student(fName, bYear, major, isGrad));
				
				}
				break;
			case 5:
				System.out.println("**** Adding 2 Courses to Faculty ***");
				System.out.print("Enter Faculty ID: ");
				fID = kb.nextInt();
				addCourseToFaculty(fID, courses[0]);
				addCourseToFaculty(fID, courses[1]);
				break;
			case 6:
				System.out.println("**** Adding 2 Courses to Student ****\n");
				System.out.print("Enter Student ID: ");
				sID = kb.nextInt();
				addCourseToStudent(sID, courses[0]);
				addCourseToStudent(sID, courses[1]);
				break;
			case 7:
				System.out.println("**** Adding 2 Courses to Faculty ****\n");
				System.out.print("Enter Faculty ID: ");
				fID = kb.nextInt();
				addCoursesToFaculty(fID, courses);
				break;
			case 8:
				System.out.println("**** Adding 2 Courses to Student ****\n");
				System.out.print("Enter Student ID: ");
				sID = kb.nextInt();
				addCoursesToStudent(sID, courses);
				break;
			case 9:
				System.out.println("**** Searching Courses of a Faculty ****\n");
				System.out.print("Enter Faculty ID: ");
				fID = kb.nextInt();
				courseCount = getNumberOfCoursesByAFaculty(fID);
				System.out.println("This faculty teaches "+ courseCount+ " courses!");
				System.out.print("Enter Course index [between 0 and " +(courseCount-1) +"]:");
				cIndex=kb.nextInt();
				System.out.println(getCourseOfFaculty(fID, cIndex).toString());
				break;
			case 10:
				System.out.println("**** Searching Courses of a Student ****\n");
				System.out.print("Enter Student ID: ");
				sID = kb.nextInt();
				courseCount = getNumberOfCoursesByAStudent(sID);
				System.out.println("This student taken "+ courseCount+ " courses!");
				System.out.print("Enter Course index [between 0 and " +(courseCount-1) +"]:");
				cIndex=kb.nextInt();
				System.out.println(getCourseOfStudent(sID, cIndex).toString());
				break;
			case 11:
				System.out.println("**** Checking if the Course taught by the Faculty ****\n");
				System.out.print("Enter Faculty ID: ");
				fID = kb.nextInt();
				System.out.print("Enter Course Number: ");
				cNum = kb.nextInt();
				searchCourseWithFaculty(fID, cNum);
				break;
			case 12:
				System.out.println("yet to implement!");
				break;
			case 13:
				return;
			default:
				System.out.println("Invalid Choice");
		}
		
	}
	}
	public static void searchCourseWithFaculty(int fid, int cnum) {
		Faculty temp = facultyList;
		boolean flag = false;
		String fName="";
		
		while(temp != null) {
			if(temp.employeeID == fid) {
				fName =  temp.getName();
				for(int index=0; index < temp.getNumCoursesTaught(); index++) {
					if(temp.getCourseTaught(index).getCourseNum() == cnum) {
						flag = true;
					}
				}
				
			break;
			}
			temp = temp.next;
		}
		if(flag) {
			System.out.println("Yes this course (# " + cnum + " is taught by "  + fName);
		}
		else {
			System.out.println("Sorry! the said faculty is not teaching this course");
		}
		
	}
	public static Course getCourseOfStudent(int sid, int cindex) {
		Student temp=studentList;
		while(temp != null) {
			if(temp.getStudentID() == sid) {
				break;
			}
			temp = temp.next;
		}
		return temp.getCourseTaken(cindex);
	}
	public static int getNumberOfCoursesByAStudent(int sid) {
		Student temp=studentList;
		while(temp != null) {
			if(temp.getStudentID() == sid) {
				break;
			}
			temp = temp.next;
		}
		if(temp != null) {
			return temp.getNumCoursesTaken();
		}
		else {
			return 0;
		}
	}
	public static Course getCourseOfFaculty(int fid, int cindex) {
		Faculty temp=facultyList;
		while(temp != null) {
			if(temp.getEmployeeID() == fid) {
				break;
			}
			temp = temp.next;
		}
		return temp.getCourseTaught(cindex);
	}
	public static int getNumberOfCoursesByAFaculty(int fid) {
		Faculty temp=facultyList;
		while(temp != null) {
			if(temp.getEmployeeID() == fid) {
				break;
			}
			temp = temp.next;
		}
		if(temp != null) {
			return temp.getNumCoursesTaught();
		}
		else {
			return 0;
		}
	}
	public static void addCourseToFaculty(int fid, Course course) {
		Faculty temp;
		temp = facultyList;
		while(temp != null) {
			if(temp.getEmployeeID() == fid) {
				temp.addCourseTaught(course);
				break;
			}
			temp=temp.next;
		}
	}
	public static void addCoursesToFaculty(int fid, Course[] course) {
		Faculty temp;
		temp = facultyList;
		int index;
		while(temp != null) {
			if(temp.getEmployeeID() == fid) {
				temp.addCoursesTaught(course);
				break;
			}
			temp=temp.next;
		}
	}
	public static void addCourseToStudent(int sid, Course course) {
		Student temp;
		temp = studentList;
		while(temp != null) {
			if(temp.getStudentID() == sid) {
				temp.addCourseTaken(course);
				break;
			}
			temp=temp.next;
		}
	}
	public static void addCoursesToStudent(int sid, Course[] course) {
		Student temp;
		temp = studentList;
		int index;
		while(temp != null) {
			if(temp.getStudentID() == sid) {
				temp.addCoursesTaken(course);
				break;
			}
			temp=temp.next;
		}
	}
	public static Course addToCourseList(Course listHead, Course newCourse) {
		Course temp;
		if (listHead==null) {
			listHead = newCourse;
		}
		else {
			temp = listHead;
			while (temp.next != null) {
				temp = temp.next;
			}	
			temp.next = newCourse;
		}
		return listHead;
	}
	public static void dispCourseList(Course listHead) {
		while(listHead!=null) {
			System.out.println(listHead.toString());
			listHead = listHead.next;
		}
	}
	public static Student addToStudentList(Student listHead, Student newStudent) {
		Student temp;
		if (listHead==null) {
			listHead = newStudent;
		}
		else {
			temp = listHead;
			while (temp.next != null) {
				temp = temp.next;
			}	
			temp.next = newStudent;
		}
		return listHead;
	}
	public static void dispStudentList(Student listHead) {
		while(listHead!=null) {
			System.out.println(listHead.toString());
			listHead = listHead.next;
		}
	}
	public static Faculty addToFacultyList(Faculty listHead, Faculty newFaculty) {
		Faculty temp;
		if (listHead==null) {
			listHead = newFaculty;
		}
		else {
			temp = listHead;
			while (temp.next != null) {
				temp = temp.next;
			}	
			temp.next = newFaculty;
		}
		return listHead;
	}
	public static void dispFacultyList(Faculty listHead) {
		while(listHead!=null) {
			System.out.println(listHead.toString());
			listHead = listHead.next;
		}
	}
	public static GeneralStaff addToStaffList(GeneralStaff listHead, GeneralStaff newStaff) {
		GeneralStaff temp;
		if (listHead==null) {
			listHead = newStaff;
		}
		else {
			temp = listHead;
			while (temp.next != null) {
				temp = temp.next;
			}	
			temp.next = newStaff;
		}
		return listHead;
	}
	public static void dispStaffList(GeneralStaff listHead) {
		while(listHead!=null) {
			System.out.println(listHead.toString());
			listHead = listHead.next;
		}
	}
	public static void saveToFile() {
		
		Course courseTemp;
		Student studentTemp;
		Faculty facultyTemp;
		GeneralStaff staffTemp;
		try {

		FileWriter fw = new FileWriter("outputDB.txt");
		String str;
		//Courses
		courseTemp = courseList;
		while(courseTemp != null) {
			str ="";
			str += "Course: ";
			str += Boolean.toString(courseTemp.isGraduateCourse()) + ",";
			str += Integer.toString(courseTemp.getCourseNum()) + ",";
			str += courseTemp.getCourseDept() + ",";
			str += Integer.toString(courseTemp.getNumCredits()) +"\n";
			fw.write(str);
			courseTemp = courseTemp.next;
		}
		fw.write("\n");	
		//Students
		studentTemp = studentList;
		while(studentTemp != null) {
			str ="";
			str += "Student: ";
			if(studentTemp.getName().length() > 0) {
				str += studentTemp.getName().trim() + ",";
			}
			if(studentTemp.getBirthYear() > 0) {
				str += Integer.toString(studentTemp.getBirthYear()) + ",";
			}
			if(studentTemp.getMajor().length() > 0) {
				str += studentTemp.getMajor().trim() + ",";
			}
			str += Boolean.toString(studentTemp.isGraduate()) + "\n";
			
			
			fw.write(str);
			studentTemp = studentTemp.next;
		}
		fw.write("\n");	
		
		//Faculty
		facultyTemp = facultyList;
		while(facultyTemp != null) {
			str ="";
			str += "Faculty: ";
			if(facultyTemp.getName().length() > 0) {
				str += facultyTemp.getName().trim() + ",";
			}
			if(facultyTemp.getBirthYear() > 0) {
				str += Integer.toString(facultyTemp.getBirthYear()) + ",";
			}
			if(facultyTemp.getDeptName().length() > 0) {
				str += facultyTemp.getDeptName().trim() + ",";
			}
			str += Boolean.toString(facultyTemp.isTenured()) + "\n";
			
			
			fw.write(str);
			facultyTemp = facultyTemp.next;
		}
		fw.write("\n");	

		//General Staff
		staffTemp = staffList;
		while(staffTemp != null) {
			str ="";
			str += "GeneralStaff: ";
			if(staffTemp.getName().length() > 0) {
				str += staffTemp.getName().trim() + ",";
			}
			if(staffTemp.getBirthYear() > 0) {
				str += Integer.toString(staffTemp.getBirthYear()) + ",";
			}
			if(staffTemp.getDeptName().length() > 0) {
				str += staffTemp.getDeptName().trim() + ",";
			}
			if(staffTemp.getDuty().length() > 0) {
				str += staffTemp.getDuty().trim() ;
			}
			str += "\n";
			fw.write(str);
			staffTemp = staffTemp.next;
		}
		fw.write("\n");	
		fw.close();
		}
		catch(Exception ex) {
		}
		
	}
}
