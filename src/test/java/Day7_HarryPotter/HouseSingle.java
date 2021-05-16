package Day7_HarryPotter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseSingle {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mascot")
    @Expose
    private String mascot;
    @SerializedName("headOfHouse")
    @Expose
    private String headOfHouse;
    @SerializedName("houseGhost")
    @Expose
    private String houseGhost;
    @SerializedName("founder")
    @Expose
    private String founder;
    @SerializedName("__v")
    @Expose
    private Integer __v;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("values")
    @Expose
    private List<String> values = null;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public HouseSingle() {
    }

    /**
     *
     * @param headOfHouse
     * @param houseGhost
     * @param mascot
     * @param school
     * @param founder
     * @param __v
     * @param members
     * @param values
     * @param name
     * @param _id
     * @param colors
     */
    public HouseSingle(String _id, String name, String mascot, String headOfHouse, String houseGhost, String founder, Integer __v, String school, List<Member> members, List<String> values, List<String> colors) {
        super();
        this._id = _id;
        this.name = name;
        this.mascot = mascot;
        this.headOfHouse = headOfHouse;
        this.houseGhost = houseGhost;
        this.founder = founder;
        this.__v = __v;
        this.school = school;
        this.members = members;
        this.values = values;
        this.colors = colors;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public String getHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(String headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public String getHouseGhost() {
        return houseGhost;
    }

    public void setHouseGhost(String houseGhost) {
        this.houseGhost = houseGhost;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

}