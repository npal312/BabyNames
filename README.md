# BabyNames
A program that produces a ranked list of the top ranked baby names per year using IRS data.

Assignment Description: (From SSW-315)

Write a program that produces a ranked list of the top ranked baby names per year from the IRS dataLinks to an external site. (do not use other data sources). 

You would need to aggregate the state-level data into two sets of tables for girls and boys.
The user would provide the base year (i.e., for which the ranking would be based) via the command line argument.
If user indicates "all" as the base year, then the total counts across all years would be used to produce the rank.
If user indicates "YYYYs" as the base year where YYYY is the year ending in a '0', then the total counts for the decade would be used to produce a rank.
Produce a comma-separated CSV file that tabulates the top 100 names for the selected year along with those names' rankings in the other years. 
First row should be:  name, 1910, 1911, ... 2020, 2021
After the first row, each row indicates the ranking of a name (sorted by the base-year ranking) for each of the years.
After the first column, each column indicates the ranking of names for the particular year. Years should be sorted from the lowest to the highest.
Note that the selected year's rankings will increase from 1 to 100, but other years would be based on the names column.
Do not use a specialized CSV library.
 

Notes:

When uploading the results of your test run, make sure to include a snapshot of your code and test results within IDE.
You can continue updating your work and submitting newer versions before the deadline. Only the last version before the deadline will be graded.
Do not replicate the data unnecessarily in your program.
Use efficient data structures in data aggregation and report generation.
 

Sample Output:

https://drive.google.com/drive/folders/1DQyF2YjdU9FafQhBHeRVRLr65e4lo2g1?usp=sharingLinks to an external site. 
Note that 0 indicates, a name was not available in the year and hence not ranked.
You may verify some of your rankings using https://www.ssa.gov/oact/babynames/top5names.htmlLinks to an external site. website
 

Submission:

Java source code named BabyNames.java
Screenshot of your test run as pdf or image (jpg, jpeg, png)
Sample output files from your run.
For example if year 2011 was chosen, you would have 2011_RankedBabyBoyNames.csv and 2011_RankedBabyGirlNames.csv
Complexity.pdf that reports the computational and storage complexity of each method.
