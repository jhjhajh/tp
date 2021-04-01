package seedu.connoisseur.messages;

public class Messages {
    public static final String LOGO = "   _____                        _                         \n"
            + "  / ____|                      (_)                        \n"
            + " | |     ___  _ __  _ __   ___  _ ___ ___  ___ _   _ _ __ \n"
            + " | |    / _ \\| '_ \\| '_ \\ / _ \\| / __/ __|/ _ \\ | | | '__|\n"
            + " | |___| (_) | | | | | | | (_) | \\__ \\__ \\  __/ |_| | |   \n"
            + "  \\_____\\___/|_| |_|_| |_|\\___/|_|___/___/\\___|\\__,_|_|   \n";

    public static final String WELCOME_MESSAGE = "                         Welcome to\n"
            + LOGO
            + "\n"
            + "Connoisseur is in review mode by default!\n"
            + "Enter 'review' or 'reco' to change mode when prompted:) ";

    public static final String COMMAND_PROMPT = "_____________________________________________________________________"
            + "______________________\n"
            + "Please enter a command: ";

    public static final String EXIT_MESSAGE = "Thanks for using Connoisseur, see you again!";
    public static final String ERROR_MESSAGE = "An error has occured";
    public static final String INVALID_COMMAND = "Invalid command, please try again.";

    // Help
    public static final String HELP_MESSAGE = "Connoisseur is an application to keep track of all your reviews and"
            + " recommendations!\n"
            + "There are two modes, which are review mode and recommendations mode.\n"
            + "This application is in review mode by default.\n\n"
            + "Commands:\n"
            + "help\t\tdisplays help on commands recognised by connoisseur\n"
            + "review\t\tswitch over to review mode\n"
            + "reco\t\tswitch over to recommendation mode\n"
            + "new/add\t\tadds a new review or recommendation\n"
            + "sort\t\tsets your preferred sorting method for your reviews\n"
            + "list\t\tlists your stored reviews or recommendations\n"
            + "edit\t\tedits an existing review or recommendation in your list\n"
            + "done\t\tmark a recommendation as done and add a review for it\n"
            + "delete\t\tdeletes a review or recommendation\n"
            + "exit/bye\texits connoisseur\n"
            + "\nType <help> followed by <command> to learn more about how each command can be used.";
    public static final String SORT_HELP_MESSAGE = "Sort will set your preferred sorting method for your reviews.\n"
            + "This command is not available in the recommendation mode.\n\n"
            + "Currently recognised methods are:\n\ttitle\n\tcategory\n\trating\n\tearliest\n\tlatest\n"
            + "\nCommand syntax: sort <method>";
    public static final String LIST_HELP_MESSAGE = "List will allow you to list all your reviews or recommendations.\n"
            + "You will list the reviews/recommendations depending on the mode you are in.\n\n"
            + "Command syntax: list";
    public static final String EDIT_HELP_MESSAGE = "Edit will allow you to edit an existing review or recommendation"
            + " in your list.\n\n"
            + "Command syntax: edit <title of review or recommendation to be edited>";
    public static final String EXIT_HELP_MESSAGE = "Exit/bye will allow you to exit the application.\n\n"
            + "Command syntax: bye/exit";
    public static final String NEW_HELP_MESSAGE = "New and Add are interchangeable. "
            + "They allow you to add a new review or recommendation,\n"
            + "depending on whether you are in review mode or recommendation mode.\n"
            + "Follow the prompts on screen to add the review/recommendation\n\n"
            + "Command syntax:\n"
            + "Review Mode:\n"
            + "\tnew/add\n"
            + "\tnew/add quick\tfor a quick review\n"
            + "\tnew/add full\tfor a full review\n\n"
            + "Recommendation Mode:\n"
            + "\tnew/add";
    public static final String DELETE_HELP_MESSAGE = "Delete will allow you to remove an entry you input previously\n\n"
            + "Command syntax: delete <title of review/recommendation>";
    public static final String VIEW_HELP_MESSAGE = "View will allow you to view the details of a review entry you"
            + " made.\nYou can view the title, ratings, date and the full description of your experience.\n\n"
            + "Command Syntax: view <title of review>";
    public static final String RECODONE_HELP_MESSAGE = "Done will allow you to mark a recommendation as done and switch"
            + " it over as a review.\n"
            + "This can only be done in recommendation mode.\n"
            + "Follow the prompts on screen to convert your recommendation to a review\n\n"
            + "Command syntax: done <title of recommendation>";
    public static final String REVIEW_MODE_MESSAGE = "Review will allow you to switch over to review mode. The default"
            + " mode of this application is review mode.\n\nCommand syntax: review";
    public static final String RECO_MODE_MESSAGE = "Reco will allow you to switch over to recommendations mode. The"
            + " default mode of this application is review mode.\n\nCommand syntax: reco";

    // Storage
    public static final String FILE_ALREADY_EXISTS = "Data file already exists";
    public static final String FILE_SUCCESS = "Created new data file";
    public static final String FOLDER_ALREADY_EXISTS = "Data folder already exists";
    public static final String FOLDER_SUCCESS = "Created new data folder";
    public static final String CURRENT_DIRECTORY = "Present project directory is: " + System.getProperty("user.dir");

    // General
    public static final String DUPLICATE_REVIEW = "There is a review in your list with the same title:";
    public static final String DUPLICATE_RECOMMENDATION = "There is a recommendation in your list with the same title:";

    // Add
    public static final String QUICK_PROMPT = "Do you want to add a quick review? (y/n)";
    public static final String REVIEW_TITLE_PROMPT = "Please enter title of review: ";
    public static final String RECO_TITLE_PROMPT = "Please enter title of recommendation: ";
    public static final String CATEGORY_PROMPT = "Please enter category of experience: ";
    public static final String RATING_PROMPT = "Please enter rating of experience (out of 5): ";
    public static final String PRICE_PROMPT = "Please enter price range of recommendation (separated by - ): ";
    public static final String DESCRIPTION_PROMPT = "Please enter description of the experience: ";
    public static final String RECOBY_PROMPT = "How did you know about this recommendation";
    public static final String LOCATION_PROMPT = "Where is this located at? (NA if not applicable)";
    public static final String ADD_SUCCESS = " has been saved to your list.";

    // Sort
    public static final String CURRENT_SORT_METHOD = "Your current sort method is: ";
    public static final String AVAILABLE_SORT_METHODS = "Recognised methods are:\n\tTITLE\n\tCATEGORY\n"
            + "\tRATING\n\tEARLIEST\n\tLATEST\n";
    public static final String SORT_METHOD_PROMPT = "Use sort <method> to change sorting method";
    public static final String INVALID_SORT_METHOD = " is not valid sorting method, please try again.";
    public static final String SORT_METHOD_SUCCESS = "Success! Your preferred sorting method has been saved: ";

    // Delete
    public static final String MISSING_DELETE_TITLE = "Please specify title of review to be deleted";
    public static final String INVALID_DELETE_REVIEW_TITLE = "Specified review does not exist!";
    public static final String INVALID_DELETE_RECO_TITLE = "Specified recommendation does not exist!";
    public static final String DELETE_SUCCESS = " has been deleted.";

    // View
    public static final String INVALID_VIEW_TITLE = "Specified review does not exist!";
    public static final String MISSING_VIEW_TITLE = "Please specify title of review you would like to view";

    //Edit
    public static final String MISSING_EDIT_TITLE = "Please specify title of review you would like to edit";
    public static final String ANYTHING_ELSE = "Would you like to edit anything else? (y/n)";
    public static final String EDIT_PROMPT_REVIEW = "What would you like to edit (Title / Category / "
            + "Rating / Description)?";
    public static final String EDIT_PROMPT_RECO = "What would you like to edit (Title / Category / "
            + "Price range / Location/ RecBy)?";
    public static final String EDIT_TITLE_PROMPT = "What would you like to change the title to?";
    public static final String EDIT_RATING_PROMPT = "What would you like to change the rating to out of 5 stars?";
    public static final String EDIT_RANGE_PROMPT = "What would you like to change the price range to? "
            + "(nearest dollar separated by - )";
    public static final String EDIT_LOCATION_PROMPT = "What would you like to change the location to?";
    public static final String EDIT_CATEGORY_PROMPT = "What would you like to change the category to?";
    public static final String EDIT_RECBY_PROMPT = "What would you like to change the recommended by to?";
    public static final String EDIT_DATE_PROMPT = "Would You like to update the date of entry for "
            + "the changes made(y/n)?";

    //Convert
    public static final String DETAILS_PROMPT = "Add in details of your experience? (y/n)";
    public static final String ENTER_DETAILS_PROMPT = "Enter the details of the review: ";
    public static final String CONVERT_SUCCESS = " has been made a review!";
}
