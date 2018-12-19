package com.company;

import utility.Trie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Trie AuthorsTrie = new Trie();
    public static Trie usersTrie = new Trie();
    public static int quotesID;
    public static ArrayList<Quote> QuotesTimeSorted = new ArrayList<>();
    public static ArrayList<Quote> QuotesVoteSorted = new ArrayList<>();

    public static void setPresentUser(User presentUser) {
        Main.presentUser = presentUser;
    }

    public static User presentUser;

    public static void main(String[] args) {
        //Main Loop
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String getIn = scanner.nextLine();
            switch (getIn) {
                case "Delete Author":
                    String name = scanner.nextLine();
                    Author instanceA = (Author) AuthorsTrie.search(name);
                    for (Quote q :
                            instanceA.quotes) {
                        q.status = "deleted";
                    }
                    break;
                case "Create User":
                    name = scanner.nextLine();
                    if (usersTrie.search(name) == null) {
                        createUser(name);
                        usersTrie.insert(name, createUser(name));
                    } else
                        System.out.println(name + " already exists.");
                    break;
                case "Delete User":
                    name = scanner.nextLine();
                    if (usersTrie.search(name) != null) {
                        User instanceUser = (User) usersTrie.search(name);
                        for (int i = 0; i < instanceUser.quotes.size(); i++) {
                            if (!instanceUser.quotes.get(i).status.equals("final")) {
                                instanceUser.quotes.get(i).status = "deleted";
                                ((Author) AuthorsTrie.search(instanceUser.quotes.get(i).author.name)).quotes.remove(instanceUser.quotes.get(i));
                                if ((((Author) AuthorsTrie.search(instanceUser.quotes.get(i).author.name)).quotes.size()) == 0)
                                    AuthorsTrie.removeItem(instanceUser.quotes.get(i).author.name);
                            }
                        }
                    } else
                        System.out.println(name + " not found.");
                    usersTrie.removeItem(name);
                    break;
                case "Login":
                    if (presentUser == null) {
                        name = scanner.nextLine();
                        if (usersTrie.search(name) != null) {
                            setPresentUser((User) usersTrie.search(name));
                            System.out.println("Logged in");
                        } else
                            System.out.println(name + " Doesn’t exist.");
                    } else System.out.println("Current user must logout first.");
                    break;
                case "Logout":
                    if (presentUser != null) {
                        setPresentUser(null);
                        System.out.println("Logged Out");
                    } else
                        System.out.println("You must login first.");
                    break;
                case "Add Quote":
                    if (presentUser != null) {
                        name = scanner.nextLine();
                        String text = scanner.nextLine();
                        Quote quote = new Quote(text);
                        Author author;
                        if (AuthorsTrie.search(name) == null) {
                            author = new Author(name);
                            author.quotes.add(quote);
                            quote.setAuthor(author);
                            AuthorsTrie.insert(name, author);
                        } else {
                            quote.setAuthor((Author) AuthorsTrie.search(name));
                            ((Author) AuthorsTrie.search(name)).quotes.add(quote);
                        }
                        quote.owner = presentUser;
                        presentUser.quotes.add(quote);
                        QuotesTimeSorted.add(quote);
                        quote.setID(QuotesTimeSorted.indexOf(quote));
                        QuotesVoteSorted.add(quote);
                    } else
                        System.out.println("You are not Logged in.");
                    break;
                case "Upvote":
                    ///Trie
                    int id;
                    if (presentUser != null) {
                        String a;
                        a = scanner.nextLine();
                        id = Integer.valueOf(a);
                        if (!presentUser.votedQuotes.contains(id)) {
                            if (!QuotesTimeSorted.get(id).status.equals("deleted")) {
                                QuotesTimeSorted.get(id).vote++;
                                presentUser.votedQuotes.add(id);
                                if (QuotesTimeSorted.get(id).vote == 3)
                                    QuotesTimeSorted.get(id).status = "final";
                            }
                        } else
                            System.out.println("You have Voted to this Quote ago.");
                    } else
                        System.out.println("You are not Logged in.");
                    break;
                case "Downvote":
                    //Trie
                    if (presentUser != null) {
                        String a;
                        a = scanner.nextLine();
                        id = Integer.valueOf(a);
                        if (!presentUser.votedQuotes.contains(id)) {
                            if (!QuotesTimeSorted.get(id).status.equals("deleted")) {
                                QuotesTimeSorted.get(id).vote--;
                                presentUser.votedQuotes.add(id);
                                if (QuotesTimeSorted.get(id).vote == -3)
                                    QuotesTimeSorted.get(id).status = "deleted";
                            }
                        } else
                            System.out.println("You have Voted to this Quote ago.");
                    } else
                        System.out.println("You are not Logged in.");
                    break;
                case "Count Votes":
                    String a;
                    a = scanner.nextLine();
                    id = Integer.valueOf(a);
                    if (!QuotesTimeSorted.get(id).status.equals("deleted")) {
                        System.out.println(QuotesTimeSorted.get(id).vote);
                    } else
                        System.out.println("Quote #" + id + " doesn't exist");
                    break;
                case "Delete Quote":
                    if (presentUser != null) {
                        a = scanner.nextLine();
                        id = Integer.valueOf(a);
                        Quote instanceQuote = QuotesTimeSorted.get(id);
                        if (!instanceQuote.status.equals("final")) {
                            instanceQuote.status = "deleted";
                            ((Author) AuthorsTrie.search(instanceQuote.author.name)).quotes.remove(instanceQuote);
                            if (((Author) AuthorsTrie.search(instanceQuote.author.name)).quotes.size() == 0)
                                AuthorsTrie.removeItem(instanceQuote.author.name);
                            presentUser.quotes.remove(instanceQuote);
                        } else
                            System.out.println("Couldn’t delete #" + id);
                    } else
                        System.out.println("You are not Logged in.");
                    break;
                case "Search by User":
                    name = scanner.nextLine();
                    if (usersTrie.search(name) != null) {
                        User instanceUser = (User) usersTrie.search(name);
                        for (int i = 0; i < instanceUser.quotes.size(); i++) {
                            if (!instanceUser.quotes.get(i).status.equals("deleted")) {
                                System.out.println("\"" + instanceUser.quotes.get(i).text + "\"");
                                System.out.println("By " + instanceUser.quotes.get(i).author.name);
                            }else
                                System.out.println("--");
                        }
                    } else
                        System.out.println(name + "Doesn't exist.");
                    break;
                case "Find All Occurrences":
                    name = scanner.nextLine();
                    int numberofSpecificQuote = 0;
                    for (Quote q : QuotesTimeSorted) {
                        if (q.text.contains(name)) {
                            numberofSpecificQuote++;
                            System.out.println("\"" + q.text + "\"");
                            System.out.println("By " + q.author.name);
                        }
                    }
                    if (numberofSpecificQuote == 0)
                        System.out.println("Couldn’t find " + name + " in any quote");
                    break;
                case "Search by ID":
                    String number;
                    number = scanner.nextLine();
                    id = Integer.valueOf(number);
                    if (!QuotesTimeSorted.get(id).status.equals("deleted")) {
                        System.out.println("\"" + QuotesTimeSorted.get(id).text + "\"");
                        System.out.println("By " + QuotesTimeSorted.get(id).author.name);
                    } else {
                        System.out.println("Quote #" + id + " doesn't exist");
                    }
                    break;
                case "Search by Author":
                    name = scanner.nextLine();
                    if (AuthorsTrie.search(name) != null) {
                        Author instanceAuthor = (Author) AuthorsTrie.search(name);
                        for (Quote quote :
                                instanceAuthor.quotes) {
                            System.out.println("\"" + quote.text + "\"");
                        }
                    } else
                        System.out.println("There is not Such Author");
                    break;
                case "Top Quotes":
                    String s = scanner.nextLine();
                    int j = Integer.valueOf(s);
                    //mergeSort(QuotesTimeSorted);
                    //mergeSort(QuotesVoteSorted,0,QuotesTimeSorted.size());
                    QuotesVoteSorted.sort(new Comparator<Quote>() {
                        @Override
                        public int compare(Quote o1, Quote o2) {
                            int result = 1;
                            if (o1.vote < o2.vote)
                                result = -1;
                            if (o1.vote < o2.vote)
                                if (o1.getID() < o2.getID())
                                    result = -1;
                            return result;
                        }
                    });
                    for (int i = 0; i < j; i++) {
                        if (!(QuotesVoteSorted.get(QuotesVoteSorted.size() - i - 1).status).equals("deleted")) {
                            System.out.println("\"" + QuotesVoteSorted.get(QuotesVoteSorted.size() - i - 1).text + "\"");
                            System.out.println("By " + QuotesVoteSorted.get(QuotesVoteSorted.size() - i - 1).author.name);
                            System.out.println();
                        }
                    }
                    break;
                case "Most Recent Quotes":
                    s = scanner.nextLine();
                    int k = Integer.valueOf(s);
                    if (k <= QuotesTimeSorted.size()) {
                        for (int i = 0; i < k; i++) {
                            if (!(QuotesTimeSorted.get(QuotesTimeSorted.size() - i - 1).status).equals("deleted")) {
                                System.out.println("\"" + QuotesTimeSorted.get(QuotesTimeSorted.size() - i - 1).text + "\"");
                                System.out.println("By " + QuotesTimeSorted.get(QuotesTimeSorted.size() - i - 1).author.name);
                                System.out.println();
                            } else
                                k++;
                        }
                    } else
                        System.out.println("There is not so much Quotes");
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println(">> Error <<");
                    break;
            }
        }
        scanner.close();
    }

    public static void createAuthor(String name) {
        Author author = new Author(name);
        AuthorsTrie.insert(name, author);
    }

    public static User createUser(String name) {
        User user = new User(name);
        return user;
    }
/*
    public static List<Comparable> merge(final List<Comparable> left, final List<Comparable> right) {
        final List<Comparable> merged = new ArrayList<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.get(0).compareTo(right.get(0)) <= 0) {
                merged.add(left.remove(0));
            } else {
                merged.add(right.remove(0));
            }
        }
        merged.addAll(left);
        merged.addAll(right);
        return merged;
    }

    public static void mergeSort(final List<Comparable> input) {
        if (input.size() != 1) {
            final List<Comparable> left = new ArrayList<Comparable>();
            final List<Comparable> right = new ArrayList<Comparable>();
            boolean logicalSwitch = true;
            while (!input.isEmpty()) {
                if (logicalSwitch) {
                    left.add(input.remove(0));
                } else {
                    right.add(input.remove(0));
                }
                logicalSwitch = !logicalSwitch;
            }
            mergeSort(left);
            mergeSort(right);
            input.addAll(merge(left, right));
        }
    }*/
}

