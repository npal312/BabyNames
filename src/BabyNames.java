import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class BabyNames {
	public String sex; //for M or F storage
	public int year; //for year corresponding to data
	public String name; //for name storage
	public int amount; //for amount of babies with said name
	public int ranking; //for storing ranking within year
	
	BabyNames(String sex, int year, String name, int amount) {
		this.sex = sex;
		this.year = year;
		this.name = name;
		this.amount = amount;
	}
	
	BabyNames(String sex, int year, String name, int amount, int ranking) {
		this.sex = sex;
		this.year = year;
		this.name = name;
		this.amount = amount;
		this.ranking = ranking;
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int getRanking() {
		return this.ranking;
	}
	
	public String toString() { //for testing purposes (doesn't get used in the code)
		return "Sex: " + sex + ", Year: " + year + ", Name: " + name + ", Amount: " + amount + ", Ranking: " + ranking;
	}
	
	
	
	
	public static TreeMap<Integer, String> findTopHundred(List<BabyNames> combinedNames, String year, String sexes){
		TreeMap<Integer, String> orderName = new TreeMap<Integer, String>();
		List<BabyNames> necessary = new ArrayList<BabyNames>();
		
		List<BabyNames> combinationNames = new ArrayList<BabyNames>(); //making and transferring combinedNames into this so it doesn't affect the values of the original list (did that before)
		for (int i = 0; i < combinedNames.size(); i++) {
			combinationNames.add(new BabyNames(combinedNames.get(i).sex, combinedNames.get(i).year, combinedNames.get(i).name, combinedNames.get(i).amount));
		}
		
		
		if (year.contains("s") == false && year.equals("all") == false) { //if only one year's data
			Comparator<BabyNames> nameCompare = Comparator.comparing(BabyNames::getYear).thenComparing(BabyNames::getSex).reversed().thenComparing(BabyNames::getAmount).reversed().thenComparing(BabyNames::getName);
			Collections.sort(combinationNames, nameCompare);
			int numYear = Integer.parseInt(year);
			boolean reached = false; //to know when you start adding to the list and when you should stop
			
			for (int i = 0; i < combinationNames.size(); i++) { //for adding the names from the same year and correct sex onto the list
				if (i != combinationNames.size() - 1) { //if not last element in list
					boolean there = combinationNames.get(i).sex.equals(sexes) && combinationNames.get(i).year == numYear; //true if the right set of data
					if (there == true) {
						necessary.add(combinationNames.get(i)); //adds to list
						reached = true;
					}
					if (reached == true && there == false) { //if no longer part of the desired data
						break;
					}
				}
				else if (i == combinationNames.size() - 1) { //if last element
					boolean there = combinationNames.get(i).sex.equals(sexes) && combinationNames.get(i).year == numYear; //true if the right set of data
					if (there == true) {
						necessary.add(combinationNames.get(i)); //adds to list
					}
				}
			}
			
			
			for (int i = 0; i < 100; i++) { //first 100 names get added to the TreeMap
				necessary.get(i).ranking = i + 1;
				orderName.put(necessary.get(i).ranking, necessary.get(i).name);
			}
			
			return orderName;
			
		}
		else if (year.contains("s") == true) { //a decade's data
			String yearNoS = year.substring(0, 4);
			int numYear = Integer.parseInt(yearNoS);
			
			List<BabyNames> timespan = new ArrayList<BabyNames>(); //for storing data in the requested timespan
			
			for (int i = 0; i < combinationNames.size(); i++) { //loop to store data in timespan
				if (combinationNames.get(i).sex.equals(sexes)) { //if right sex
					if (combinationNames.get(i).year < numYear + 10 && combinationNames.get(i).year >= numYear) { //if in between years
						timespan.add(combinationNames.get(i));
					}
					else if (combinationNames.get(i).year < numYear || combinationNames.get(i).year >= numYear + 10) { //if outside of range
					}
				}
				else if (combinationNames.get(i).sex.equals(sexes) == false) { //also if outside of range
				}
			}
			
			//same names next to each other for simpler adding
			Comparator<BabyNames> nameCompare = Comparator.comparing(BabyNames::getName);
			Collections.sort(timespan, nameCompare);

			for (int i = 0; i < timespan.size(); i++) {
				if (i != timespan.size() - 1) { //not last item in list
					if (timespan.get(i).name.equals(timespan.get(i+1).name)) { //if there is another duplicate
						timespan.get(i+1).amount += timespan.get(i).amount;
					}
					else if (timespan.get(i).name.equals(timespan.get(i+1).name) == false) { //if there are no more duplicates
						necessary.add(timespan.get(i));
					}
				}
				else { //if last item in list (either a duplicate with values pushed to it or not a duplicate, but should go on list either way)
					necessary.add(timespan.get(i));
				}
			}
			
			//same sorting as in dataAggregation (same effect is desired with this data)
			nameCompare = Comparator.comparing(BabyNames::getAmount).reversed().thenComparing(BabyNames::getName);
			Collections.sort(necessary, nameCompare);
			
			for (int i = 0; i < 100; i++) { //first 100 names get added to the TreeMap
				necessary.get(i).ranking = i + 1;
				orderName.put(necessary.get(i).ranking, necessary.get(i).name);
			}
			
			return orderName;
			
		}
		else if (year.equals("all")) { //if every year's data
			//sort by sex and then name so all names of same sex are next to each other
			Comparator<BabyNames> nameCompare = Comparator.comparing(BabyNames::getSex).thenComparing(BabyNames::getName);
			Collections.sort(combinationNames, nameCompare);
			
			for (int i = 0; i < combinationNames.size(); i++) { //for combining name duplicates with same sex (year doesn't matter in this case)
				if (i != combinationNames.size() - 1) { //if not last element in list
					boolean prev = false; //allows first if statement to trigger just in case it isn't a duplicate of what comes after
					if (i != 0) { //stops index(-1) error from occurring
						//checks if previous BabyNames and this one are the same (used to make sure the end of duplications doesn't get readded onto the list)
						prev = combinationNames.get(i).name.equals(combinationNames.get(i-1).name);
					}
					//checks if next BabyNames and this one are the same (used to flag duplicates)
					boolean next = combinationNames.get(i).name.equals(combinationNames.get(i+1).name);
					
					boolean life = false;
					
					boolean death = combinationNames.get(i).sex.equals(sexes) == false; //called death because the loop ends here
					
					if (life && death) { //if sex changes then leave the loop immediately
						break;
					}
					if (prev == false && next == false && combinationNames.get(i).sex.equals(sexes)) { //not a duplicate (it is not the same as the one before nor the one after)
						necessary.add(combinationNames.get(i)); //adds to list
						life = true;
					}
					//if BabyNames are similar in every way except amount (means they're duplicates and must be combined)
					if (next == true && combinationNames.get(i).sex.equals(sexes)) {
						combinationNames.get(i+1).amount += combinationNames.get(i).amount; //combines amounts together (and this way it'll still combine with multiple duplicates in a row)
						life = true;
					}
					if (prev == true && next == false && combinationNames.get(i).sex.equals(sexes)) { //if the above is not true (so no more duplicate) but was duplicate in the past
						necessary.add(combinationNames.get(i)); //adds value with combined values
						life = true;
					}
				}
				else if (i == combinationNames.size() - 1) { //if last element
					if (combinationNames.get(i).sex.equals(sexes)) {
						necessary.add(combinationNames.get(i)); //adds to list
					}
				}
			}
			
			//same sorting as in dataAggregation (same effect is desired with this data)
			nameCompare = Comparator.comparing(BabyNames::getAmount).reversed().thenComparing(BabyNames::getName);
			Collections.sort(necessary, nameCompare);
			
			
			for (int i = 0; i < 100; i++) { //first 100 names get added to the TreeMap
				necessary.get(i).ranking = i + 1;
				orderName.put(necessary.get(i).ranking, necessary.get(i).name);
			}
			
			return orderName;
			
		}
		
		return new TreeMap<Integer, String>();
	}
	
	
	
	
	public static void csvFile(HashMap<Integer, HashMap<String, Integer>> data, TreeMap<Integer, String> order, String year, String sex) throws FileNotFoundException{
		String sexes = "";
		if (sex.equals("M")) sexes = "Boy";
		else if (sex.equals("F")) sexes = "Girl";
		String fileName = year + "_RankedBaby" + sexes + "Names.csv";
		
		PrintWriter logger = new PrintWriter(fileName);
		
		List<Integer> values = new ArrayList<Integer>(data.keySet()); //gets the years in order to use for HashMap
		logger.print("name");
		for (Integer i: values) {
			logger.print("," + i); //makes them comma-separated values (csv type stuff)
		}
		logger.print("\n");
		
		for (int i = 1; i <= 100; i++) {
			String nameVal = order.get(i); //retrieves order of names
			logger.print(nameVal);
			
			for (Integer j: values) { //accesses every HashMap in the Nested HashMap
				logger.print("," + findData(data.get(j), nameVal)); //adds the data to the file
			}
			
			logger.print("\n");
		}
		logger.close();
		
	}
	
	
	
	
	public static int findData(HashMap<String, Integer> data, String key) {
		if (data.containsKey(key)) { //if key is contained in HashMap
			return data.get(key); //getting values with HashMap is easy (if it's there)
		}
		else { //if key is not contained in HashMap
			return 0;
		}
		
	}
	
	
	
	
	public static void dataAggregation(String year) throws FileNotFoundException { //void for now
		File directory = new File("resource/namesbystate"); //where the files are stored
		File[] files = directory.listFiles();
		
		System.out.println("\nPlease wait, this may take a minute.\n");
		
		List<BabyNames> fileInfo = new ArrayList<BabyNames>(); //for holding every value from all the files(in BabyNames form)
		for (int i = 0; i < files.length; i++) {
			Scanner fileContents = new Scanner(files[i]);
			while(fileContents.hasNext()) {
				String contents = fileContents.nextLine();
				String[] separated = contents.split(",");
				int yearName = Integer.parseInt(separated[2]);
				int amount = Integer.parseInt(separated[4]);
				fileInfo.add(new BabyNames(separated[1], yearName, separated[3], amount));
			}
			fileContents.close();
		}
		
		System.out.println("Loading... 25%"); //for fun (bc this program will prob take a long time)
		
		int name = 0;
		for (int i = 0; i < fileInfo.size(); i++) {
			name += fileInfo.get(i).amount;
		}
		System.out.println(fileInfo.size());
		System.out.println(name);
		
		//Sorts by Year and Sex and Name in ascending order (makes it so all ones with same values for these three are next to each other so combining duplicates can happen)
		Comparator<BabyNames> nameCompare = Comparator.comparing(BabyNames::getYear).thenComparing(BabyNames::getSex).thenComparing(BabyNames::getName);
		Collections.sort(fileInfo, nameCompare);
		
		System.out.println("Loading... 50%");
		
		List<BabyNames> combinedNames = new ArrayList<BabyNames>(); //to store combined duplicates (adding to new array is faster than removing from old array so doing this)
		
		for (int i = 0; i < fileInfo.size(); i++) { //for combining duplicates and adding non duplicates to the new list while ignoring the end of duplication chains
			if (i != fileInfo.size() - 1) { //if not last element in list
				boolean prev = false; //allows first if statement to trigger just in case it isn't a duplicate of what comes after
				if (i != 0) { //stops index(-1) error from occurring
					//checks if previous BabyNames and this one are the same (used to make sure the end of duplications doesn't get readded onto the list)
					prev = fileInfo.get(i).sex.equals(fileInfo.get(i-1).sex) && fileInfo.get(i).year == fileInfo.get(i-1).year && fileInfo.get(i).name.equals(fileInfo.get(i-1).name);
				}
				//checks if next BabyNames and this one are the same (used to flag duplicates)
				boolean next = fileInfo.get(i).sex.equals(fileInfo.get(i+1).sex) && fileInfo.get(i).year == fileInfo.get(i+1).year && fileInfo.get(i).name.equals(fileInfo.get(i+1).name);
				
				if (prev == false && next == false) { //not a duplicate (it is not the same as the one before nor the one after)
					combinedNames.add(fileInfo.get(i)); //adds to list
				}
				//if BabyNames are similar in every way except amount (means they're duplicates and must be combined)
				if (next == true) {
					fileInfo.get(i+1).amount += fileInfo.get(i).amount; //combines amounts together (and this way it'll still combine with multiple duplicates in a row)
				}
				if (prev == true && next == false) { //if the above is not true (so no more duplicate) but was duplicate in the past
					combinedNames.add(fileInfo.get(i)); //adds value with combined values
				}
			}
			else if (i == fileInfo.size() - 1) { //if last element
				combinedNames.add(fileInfo.get(i)); //adds to list
			}
		}
		
		System.out.println("Loading... 60%");
				
		TreeMap<Integer, String> orderGirl = findTopHundred(combinedNames, year, "F");
		
		System.out.println("Loading... 65%");	
		
		TreeMap<Integer, String> orderBoy = findTopHundred(combinedNames, year, "M");
		
		System.out.println("Loading... 70%");
						
			
		
		//Sorts by Year and Sex in ascending order, reverses (makes descending), sorts by Amount in ascending order, reverses (so Y and S are ascending again and A is descending), and sorts by Name (ascending)
		//Starts at first year in data with the girl names first (F earlier than M) and goes from most common name (largest amount) to least common (smallest amount)
		//If two names are in the same year, have the same sex, and have the same amount, the name earlier in the alphabet goes first
		nameCompare = Comparator.comparing(BabyNames::getYear).thenComparing(BabyNames::getSex).reversed().thenComparing(BabyNames::getAmount).reversed().thenComparing(BabyNames::getName);
		Collections.sort(combinedNames, nameCompare);
		
		System.out.println("Loading... 80%");		
		
		//Nested HashMap with year as key and inner HashMaps with name as key (ranking as value)
		HashMap<Integer, HashMap<String, Integer>> boys = new HashMap<Integer, HashMap<String, Integer>>(); //for boys' data
		HashMap<Integer, HashMap<String, Integer>> girls = new HashMap<Integer, HashMap<String, Integer>>(); //for girls' data		
		HashMap<String, Integer> data = new HashMap<String, Integer>(); //for storing the HashMap about to be added to the nested HashMap
		int rank = 1; //ranking variable (can't be based on i because of all data points being in one list)
		
		for (int i = 0; i < combinedNames.size(); i++) {
			combinedNames.get(i).ranking = rank;
			data.put(combinedNames.get(i).name, combinedNames.get(i).ranking); //adds to temp HashMap
			rank++; //incrementing rank by 1 for next data point
			if (i != combinedNames.size() - 1) { //if not last element in list
				if (combinedNames.get(i).sex.equals(combinedNames.get(i+1).sex) == false || combinedNames.get(i).year != combinedNames.get(i+1).year) { //if moving to next batch of data
					if (combinedNames.get(i).sex.equals("F")) { //if girls' names
						girls.put(combinedNames.get(i).year, data); //put in correct map
					}
					else if (combinedNames.get(i).sex.equals("M")) { //if boys' names
						boys.put(combinedNames.get(i).year, data); //put in correct map
					}
					data = new HashMap<String, Integer>(); //resets the HashMap for the next batch
					rank = 1; //reset ranking so next batch starts fresh
				}
				else { //not moving to next batch of data
				}
			}
			else if (i == combinedNames.size() - 1) { //last element in list
				if (combinedNames.get(i).sex.equals("F")) { //if girls' names
					girls.put(combinedNames.get(i).year, data); //put in correct map
				}
				else if (combinedNames.get(i).sex.equals("M")) { //if boys' names
					boys.put(combinedNames.get(i).year, data); //put in correct map
				}
			}
		}
		
		System.out.println("Loading... 90%");
		
		csvFile(boys, orderBoy, year, "M");
		
		System.out.println("Loading... 95%");
		
		csvFile(girls, orderGirl, year, "F");
		
		System.out.println("Loading... 100%");
		System.out.println("\nYour .csv files for Baby Boy Names and Baby Girl Names are complete!");
		
	}
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Enter the year/years you would like to access data from: ");
		Scanner input = new Scanner(System.in);
		String year = input.nextLine();
		input.close();
		
		//checking if String input is valid here because if it's not then it'll take a long time for the program to run only to realize it's not a valid input
		char[] valid = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 's'};
		
		if (year.equals("all")) { //checks if "all" is the string
			
			dataAggregation(year);
			return;
		}
		
		boolean allowed = false;
		for (int i = 0; i < year.length(); i++) { //checks if any invalid characters are here
			for (int j = 0; j < valid.length; j++) {
				if (year.charAt(i) == valid[j]) {
					allowed = true;
				}
			}
			if (allowed == false) {
				System.out.println("Invalid input!"); //did this to avoid errors but still end program
				return;
			}
			else {
				allowed = false;
			}
		}
		
		if (year.contains("s") == false) { //if single year
			int numCheck = Integer.parseInt(year);
			if (numCheck >= 1910 && numCheck <= 2021) {
				
				dataAggregation(year);
				return;
			}
			else {
				System.out.println("Invalid input!");
				return;
			}
		}
		else { //if year.contains("s") == true  (if decade)
			if (year.length() != 5 || year.charAt(3) != '0') { //if not right size and not a decade
				System.out.println("Invalid input!");
				return;
			}
			String yearNoS = year.substring(0, 4);
			
			int numCheck = Integer.parseInt(yearNoS);
			if (numCheck >= 1910 && numCheck <= 2021) {
				
				dataAggregation(year);
				return;
			}
			else {
				System.out.println("Invalid input!"); //did this to avoid errors but still end program
				return;
			}
		}
		
	}
	
}
