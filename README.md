# Baby Names

## Introduction

Every year the U.S. Government releases a [data set](https://www.ssa.gov/oact/babynames/) containing information about baby names, from the most popular names to less common ones.
This repository contains a Java program to read CSV files and information about baby names from 1880 to 2014.

For the most part this program uses a simple ranking algorithm to give insights on name. It assumes the data is already sorted from the most popular to the less one. 

## Methods and description:

* **totalBirths** Return total births of boys identified in the data set as Gender "M", and total births for girls identified in the data set as Gender "F" per year.

* **getRank** Return the rank of the name in the file for the given gender, where rank 1 is the name with the largest number of births.

* **getName** Return the name of the person in the file at this rank, for the given gender, where rank 1 is the name with the largest number of births.

* **WhatIsNameInYear** Determine what would your name be if you were born in a different year, based on popularity.

* **yearOfHighestRank** Select a range of files and return the year with the highest rank for the name and gender.

* **getAverageRank** Select a range of files to process and returns the average rank of the name and gender over the selected files.

* **getTotalBirthsRankedHigher** Return an integer, the total number of births of those names with the same gender and same year who are ranked higher than name.
