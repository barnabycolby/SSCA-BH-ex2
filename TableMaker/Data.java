package ex2.TableMaker;

import java.sql.*;
import java.util.Random;

public class Data {

	private static Random randomGenerator;
	private static int totalInserts = Data.getNumberOfTitles()
			+ Data.getNumberOfStudents()
			+ Data.getNumberOfLecturers() + Data.getNumberOfCourses()
			+ Data.getNumberOfSessions() + Data.getNumberOfTypes()
			+ Data.getNumberOfMarks()
			+ Data.getNumberOfStudentContacts()
			+ Data.getNumberOfNextOfKins();
	private static int completedInserts = 0;

	public static void add(Connection conn) throws SQLException {
		String prefix = "Adding data...";

		Data.addTitles(conn, prefix);
		Data.addStudent(conn, prefix);
		Data.addLecturers(conn, prefix);
		Data.addCourse(conn, prefix);
		Data.addSession(conn, prefix);
		Data.addType(conn, prefix);
		Data.addMarks(conn, prefix);
		Data.addStudentContact(conn, prefix);
		Data.addNextOfKin(conn, prefix);

		System.out.println("\r" + prefix
				+ "Done.                                     ");
	}

	private static int getNumberOfTitles() {
		return 10;
	}

	private static int getNumberOfStudents() {
		return 200;
	}

	private static int getNumberOfLecturers() {
		return 10;
	}

	private static int getNumberOfCourses() {
		return 200;
	}

	private static int getNumberOfSessions() {
		return 2;
	}

	private static int getNumberOfTypes() {
		return 3;
	}

	private static int getNumberOfMarks() {
		return 200;
	}

	private static int getNumberOfStudentContacts() {
//		return getNumberOfStudents();
		return 100;
	}

	private static int getNumberOfNextOfKins() {
		return 100;
	}

	private static void addTitles(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "titles         "
				+ Data.getPercentageString());

		String[] titles = { "Ms", "Miss", "Mrs", "Rev", "Dr", "Prof", "Hon",
				"Gov", "Mr", "Fr" };

		String queryString = "INSERT INTO Titles VALUES ";
		String parameterString = "(?,?)";
		queryString += parameterString;
		for (int i = 1; i < titles.length; i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < titles.length; i++) {
			query.setInt((i * 2) + 1, i + 1);
			query.setString((i * 2) + 2, titles[i]);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfTitles();
	}

	private static void addStudent(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "students       "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO Student (titleID,forename,familyname,dateOfBirth,sex) VALUES ";
		String parameterString = "(?,?,?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfStudents(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfStudents(); i++) {
			String sex = Data.generateSex();
			int titleID = Data.generateTitle(sex);
			String forename = Data.generateForename(sex);
			String familyname = Data.generateFamilyname();
			Date dateOfBirth = Data.generateDateOfBirth();

			query.setInt((i * 5) + 1, titleID);
			query.setString((i * 5) + 2, forename);
			query.setString((i * 5) + 3, familyname);
			query.setDate((i * 5) + 4, dateOfBirth);
			query.setString((i * 5) + 5, sex);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfStudents();
	}

	private static void addLecturers(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "lecturers      "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO Lecturer (titleID,forename,familyname) VALUES ";
		String parameterString = "(?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfLecturers(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfLecturers(); i++) {
			String sex = Data.generateSex();
			int titleID = Data.generateTitle(sex);
			String forename = Data.generateForename(sex);
			String familyname = Data.generateFamilyname();

			query.setInt((i * 3) + 1, titleID);
			query.setString((i * 3) + 2, forename);
			query.setString((i * 3) + 3, familyname);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfLecturers();
	}

	private static void addCourse(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "course         "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO Course (courseName,courseDescription,lecturerID) VALUES ";
		String parameterString = "(?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfCourses(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfCourses(); i++) {
			int lecturerID = Data.generateLecturer();
			String coursename = "Course " + i;
			String coursedescription = "Course Description " + (i+1);

			query.setString((i * 3) + 1, coursename);
			query.setString((i * 3) + 2, coursedescription);
			query.setInt((i * 3) + 3, lecturerID);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfCourses();

	}

	private static void addSession(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "session        "
				+ Data.getPercentageString());

		String[] sessionStrings = { "May", "August" };

		String queryString = "INSERT INTO Session (sessionString) VALUES ";
		String parameterString = "(?)";
		queryString += parameterString;
		for (int i = 1; i < sessionStrings.length; i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < sessionStrings.length; i++) {
			query.setString(i + 1, sessionStrings[i]);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfSessions();
	}

	private static void addType(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "type           "
				+ Data.getPercentageString());

		String[] typeStrings = { "Normal", "Resit", "Repeat" };

		String queryString = "INSERT INTO Type (typeString) VALUES ";
		String parameterString = "(?)";
		queryString += parameterString;
		for (int i = 1; i < typeStrings.length; i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < typeStrings.length; i++) {
			query.setString(i + 1, typeStrings[i]);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfTypes();
	}

	private static void addMarks(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "marks          "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO Marks VALUES ";
		String parameterString = "(?,?,?,?,?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfMarks(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfMarks(); i++) {
			int studentID = Data.generateStudentID();
			int courseID = Data.generateCourseID();
			int year = Data.generateCourseYear();
			int sessionID = Data.generateSessionID();
			int typeID = Data.generateTypeID();
			int mark = Data.generateExamMark();
			String notes = "Notes " + i;

			query.setInt((i * 7) + 1, studentID);
			query.setInt((i * 7) + 2, courseID);
			query.setInt((i * 7) + 3, year);
			query.setInt((i * 7) + 4, sessionID);
			query.setInt((i * 7) + 5, typeID);
			query.setInt((i * 7) + 6, mark);
			query.setString((i * 7) + 7, notes);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfMarks();
	}

	private static void addStudentContact(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "StudentContact "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO StudentContact VALUES ";
		String parameterString = "(?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfStudentContacts(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfStudentContacts(); i++) {
			int studentID = Data.generateStudentID();
			String eMailAddress = Data.generateEmailAddress();
			String postalAddress = Data.generatePostalAddress();

			query.setInt((i * 3) + 1, studentID);
			query.setString((i * 3) + 2, eMailAddress);
			query.setString((i * 3) + 3, postalAddress);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfStudentContacts();
	}

	private static void addNextOfKin(Connection conn, String prefix)
			throws SQLException {
		System.out.print("\r" + prefix + "NextOfKin      "
				+ Data.getPercentageString());

		String queryString = "INSERT INTO NextOfKin VALUES ";
		String parameterString = "(?,?,?)";
		queryString += parameterString;
		for (int i = 1; i < Data.getNumberOfNextOfKins(); i++) {
			queryString += "," + parameterString;
		}

		PreparedStatement query = conn.prepareStatement(queryString);
		for (int i = 0; i < Data.getNumberOfNextOfKins(); i++) {
			int studentID = Data.generateStudentID();
			String eMailAddress = Data.generateEmailAddress();
			String postalAddress = Data.generatePostalAddress();

			query.setInt((i * 3) + 1, studentID);
			query.setString((i * 3) + 2, eMailAddress);
			query.setString((i * 3) + 3, postalAddress);
		}

		query.executeUpdate();
		completedInserts += Data.getNumberOfNextOfKins();
	}

	private static String generateEmailAddress() {
		String[] emails = { "stone@meekness.com", "ca-tech@dps.centrin.net.id",
				"trinanda_lestyowati@telkomsel.co.id",
				"asst_dos@astonrasuna.com", "amartabali@dps.centrin.net.id",
				"achatv@cbn.net.id", "bali@tuguhotels.com",
				"baliminimalist@yahoo.com", "bliss@thebale.com",
				"adhidharma@denpasar.wasantara.net.id",
				"centralreservation@ramayanahotel.com",
				"apribadi@balimandira.com", "cdagenhart@ifc.org",
				"dana_supriyanto@interconti.com", "dos@novotelbali.com",
				"daniel@hotelpadma.com", "daniel@balibless.com",
				"djoko_p@jayakartahotelsresorts.com",
				"expdepot@indosat.net.id", "feby.adamsyah@idn.xerox.com",
				"christian_rizal@interconti.com", "singgih93@mailcity.com",
				"idonk_gebhoy@yahoo.com", "info@houseofbali.com",
				"kyohana@toureast.net", "sales@nusaduahotel.com",
				"jayakarta@mataram.wasantara.net.id", "mapindo@indo.net.id",
				"sm@ramayanahotel.com", "anekabeach@dps.centrin.net.id",
				"yogya@jayakartahotelsresorts.com",
				"garudawisatajaya@indo.net.id", "ketut@kbatur.com",
				"bondps@bonansatours.com", "witamgr@dps.centrin.net.id",
				"dtedja@indosat.net.id", "info@stpbali.ac.id",
				"baliprestigeho@dps.centrin.net.id", "pamilu@mas-travel.com",
				"amandabl@indosat.net.id", "marketing@csdwholiday.com",
				"luha89@yahoo.com", "indahsuluh2002@yahoo.com.sg",
				"imz1991@yahoo.com", "gus_war81@yahoo.com",
				"kf034@indosat.net.id", "800produkwil@posindonesia.co.id",
				"kontak.synergi@yahoo.com", "oekaoeka@yahoo.com",
				"fitrianti@hotmail.com", "meylina310@yahoo.com",
				"h4ntoro@yahoo.com", "novi_enbe@yahoo.com",
				"dila_dewata@yahoo.co.id", "tiena_asfary@yahoo.co.id",
				"da_lawoffice@yahoo.com", "rini@ncsecurities.biz",
				"sudarnoto_hakim@yahoo.com", "wastioke@yahoo.com",
				"leebahri@yahoo.com.", "lia_kiara97@yahoo.com",
				"rido@weddingku.com", "b_astuti@telkomsel.co.id",
				"garudawisata@indo.net.id", "grfurniture@yahoo.com",
				"gosyen2000@hotmail.com", "hvhfood@indosat.net.id",
				"hr@astonbali.com", "hary@wibisono-family.com",
				"fadlycak'p@yahoo.com", "ida_sampurniah@telkomsel.co.id",
				"muslim-pariwisata-bali@yahoogroups.com",
				"harisnira@yahoo.com", "sales@houseofbali.com",
				"baim_ron@yahoo.com", "ilhambali222@yahoo.com",
				"bungjon@gmail.com", "diar@bdg.centrin.net.id",
				"elmienruge@hotmail.com", "galaxygarden2006@yahoo.com",
				"gorisata@indosat.net.id", "maulitasarihani@yahoo.com",
				"hamiluddakwah@gmail.com.au", "bounty@indo.net.id" };

		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(emails.length);
		return emails[randomInt];
	}

	private static String generatePostalAddress() {
		String[] addresses = { "98 Abbey Row,CM5 2ZA",
				"68 East Street,SA19 9HH", "65 Marcham Road,HS2 6LE",
				"31 Worthy Lane,PE23 9EY", "86 Huntly Street,DG8 6TP",
				"31 Thirsk Road,BS24 0WT", "69 Northgate Street,OX6 9SW",
				"43 Petworth Rd,LE17 9UG", "55 Newgate Street,DD10 2GS",
				"60 High Street,EX17 9UY", "46 Thompsons Lane,BA11 7WN",
				"39 Holburn Lane,PA77 2PN", "37 Ramsgate Rd,EX14 3JT",
				"5 Abingdon Road,S66 7JR", "40 Trinity Crescent,YO19 6YS",
				"22 Seafield Place,PH16 9RF", "39 Middlewich Road,SY4 7JN",
				"9 Cloch Rd,DG9 8YE", "17 Stamford Road,SG9 0SA",
				"60 Manor Way,YO5 4WY", "45 Sea Road,SA1 8BB",
				"69 Gloucester Road,SA20 0HL", "25 Hexham Road,SO21 4DF",
				"47 Lammas Street,YO25 9PY", "14 Asfordby Rd,PH41 5QE",
				"82 Ploughley Rd,PE9 2JW", "65 Shannon Way,CT3 6RW",
				"81 Ilchester Road,EH39 9TY", "91 Redcliffe Way,PR4 0QD",
				"22 Wrexham Road,HG4 2ZR", "79 Hendford Hill,CB8 3WR",
				"40 George Street,CH4 3AH", "10 Walwyn Rd,SP10 6XL",
				"25 Wade Lane,CM9 5FS", "16 Newmarket Road,ML11 4NS",
				"45 Temple Way,DL14 5EL", "82 Ploughley Rd,EX23 8XU",
				"54 Crown Street,W2 0GS", "75 Helland Bridge,RG20 1HE",
				"17 Terrick Rd,HS2 8PJ", "30 Wade Lane,ZE2 8AL",
				"86 Stone St,IV4 6PU", "12 Emerson Road,FK8 7DY",
				"22 Wrexham Road,PE18 5YE", "76 Cefn Road,IV1 9HG",
				"38 Fordham Rd,PL32 8QQ", "40 Grenoble Road,LE14 9JL",
				"27 St Omers Road,PE20 0FJ", "83 Guildry Street,LD4 0DW",
				"52 Helland Bridge,EH52 0RF" };

		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(addresses.length);
		return addresses[randomInt];
	}

	private static int generateExamMark() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return randomGenerator.nextInt(100);
	}

	private static int generateCourseYear() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(5 - 1)) + 1;
	}

	private static int generateTypeID() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(Data.getNumberOfTypes())) + 1;
	}

	private static int generateSessionID() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(Data.getNumberOfSessions())) + 1;
	}

	private static int generateCourseID() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(Data.getNumberOfCourses())) + 1;
	}

	private static int generateStudentID() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(Data.getNumberOfStudents())) + 1;
	}

	private static int generateLecturer() {
		if (randomGenerator == null)
			randomGenerator = new Random();

		return (randomGenerator.nextInt(Data.getNumberOfLecturers())) + 1;
	}

	private static Date generateDateOfBirth() {
		if (randomGenerator == null)
			randomGenerator = new Random();
		int year = 1996 - randomGenerator.nextInt(10);
		int month = randomGenerator.nextInt(11) + 1;
		int day = randomGenerator.nextInt(27) + 1;
		
		String dateString = year + "-" + month + "-" + day;

		return java.sql.Date.valueOf(dateString);
	}

	private static String generateFamilyname() {
		String[] surnames = { "Jackson", "Aikens", "Besteman", "Bosley",
				"Knight", "Price", "Parks", "Dear", "Woodgate", "Moody",
				"Money", "Swedene", "O'Neal", "McGahey", "MacLaren", "Thomas",
				"Thompson", "Harrington", "Brown", "Black", "Berry", "Ball",
				"Sawyer", "Savoie", "Cowan", "Kenn", "Fitzpatrick",
				"Fitzgerald", "Conboy", "Page", "Craven", "Martin", "Myton",
				"O'Bryan", "Panik", "Zielke", "Zain", "Bradley", "Patrick",
				"Olson", "Vollick", "Whealy", "Sabourin", "Giles", "Gleason",
				"Laprade", "Thorne", "Weber", "Hovie", "Gordon", "Nelson",
				"MacDonald", "Jones" };
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(surnames.length);
		return surnames[randomInt];
	}

	private static String generateForename(String sex) {
		if (sex.equals("Male")) {
			return Data.generateMaleForename();
		} else {
			return Data.generateFemaleForename();
		}
	}

	private static String generateMaleForename() {
		String[] maleNames = { "Randal", "Chauncey", "Lenny", "Caleb",
				"Wilburn", "Edwardo", "Weldon", "Jacob", "Lindsey", "Archie",
				"Douglas", "Harry", "Doyle", "Cornelius", "Gustavo", "Dick",
				"Reynaldo", "Boyd", "Ted", "Clyde", "Steven", "Cristobal",
				"Cole", "Hubert", "Heath", "Timmy", "Efren", "Everette",
				"Craig", "Taylor", "Arlen", "Francesco", "Robert", "Herman",
				"Barney", "Billy", "Julius", "Randy", "Christopher", "Wm",
				"Zachery", "Javier", "Drew", "Jaime", "Graig", "Merrill",
				"Roscoe", "Mohammed", "Cyril", "Arnoldo" };
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(maleNames.length);
		return maleNames[randomInt];
	}

	private static String generateFemaleForename() {
		String[] femaleNames = { "Thuy", "Janita", "Deonna", "Eva", "Wilma",
				"Nobuko", "Sherrie", "Latina", "Glendora", "Hildred", "Ilda",
				"Hanh", "Kelli", "Ozell", "Kesha", "Judith", "Lydia",
				"Roselyn", "Latisha", "Hester", "Krysten", "Rosalina", "Jung",
				"Catharine", "Bernardina", "Brigitte", "Dede", "Shenita",
				"Dinah", "Deanna", "Terina", "Francene", "Allison", "Irma",
				"Brenna", "Brandie", "Filomena", "Raylene", "Concha",
				"Jesenia", "Lakisha", "Pok", "Leonora", "Yoko", "Veronika",
				"Ardelle", "Tamara", "Alla", "Valerie", "Mayra" };
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(femaleNames.length);
		return femaleNames[randomInt];
	}

	private static int generateTitle(String sex) {
		if (sex.equals("Male")) {
			return Data.generateFemaleTitle();
		} else {
			return Data.generateMaleTitle();
		}
	}

	private static int generateFemaleTitle() {
		// Can be id between 1 and 8 inclusive
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = (randomGenerator.nextInt(8)) + 1;
		return randomInt;
	}

	private static int generateMaleTitle() {
		// Can be id between 4 and 10 inclusive
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = (randomGenerator.nextInt(6)) + 4;
		return randomInt;
	}

	private static String generateSex() {
		if (randomGenerator == null)
			randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(2);

		if (randomInt == 0) {
			return "Male";
		} else {
			return "Female";
		}
	}

	private static String getPercentageString() {
		int stringLength = 20;
		int equalsLength = stringLength - 1;
		float fraction = (float) completedInserts / (float) totalInserts;
		int numberOfEquals = Math.round((fraction * (float) equalsLength));
		int numberOfSpaces = equalsLength - numberOfEquals;
		int percent = Math.round(fraction * 100);
		String returnString = "[";
		for (int i = 0; i < numberOfEquals; i++) {
			returnString += "=";
		}
		returnString += ">";
		for (int i = 0; i < numberOfSpaces; i++) {
			returnString += " ";
		}
		returnString += "] ";

		if (percent < 100)
			returnString += " ";
		if (percent < 10)
			returnString += " ";

		returnString += percent + "%";

		return returnString;
	}

}
