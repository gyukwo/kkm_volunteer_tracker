package soccer.model;

/* We will create a "model" object for most of our database entities. */
public class League {
    private int leagueId;
    private String leagueName;
    private boolean hasTeams;   // Indicates if the there are teams for this league.
                                // If a league has a team, we can't delete it.

    public League(int leagueId, String leagueName, boolean hasTeams) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.hasTeams = hasTeams;
    }

    public League(String leagueName) {
        leagueId = -1;
        this.leagueName = leagueName;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueID(int leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public boolean hasTeams() {
        return hasTeams;
    }    
}
    

    


    
    
    