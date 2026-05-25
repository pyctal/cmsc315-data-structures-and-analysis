# Perform set operations on tree sets

Write a program as follows:

- Create two tree sets for strings using alphabetical order.
- Prompt the user to enter two lines of strings. Strings are separated by spaces in a line.
- Extract the strings from the first/second line and add them to the first/second tree set, respectively.
- Display their union, difference, and intersection of the two tree sets. Use the set addAll, removeAll, and retainAll methods to perform union, difference, and intersection.

(Hint: You can clone the sets to preserve the original sets from being changed by these set methods.)

## Sample Run

    Enter strings for the first set: Red Green Blue White Black Tan
    Enter strings for the second set: Red Orange Black Gray Pink
    The union of the two sets is [Black, Blue, Gray, Green, Orange, Pink, Red, Tan, White]
    The difference of the two sets is [Blue, Green, Tan, White]
    The intersection of the two sets is [Black, Red]
