import java.util.Date;

public class Individual {
	
	private String ID;
	private String Name;
	private String Gender;
	private Date Birthday;
	private int Age;
	private boolean Alive = true;
	private Date Death;
	private String Child;
	private String Spouse;
	
	public String getId() {
		return ID;
	}
	public void setId(String id) {
		ID = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		this.Gender = gender;
	}
	public Date getBirthDate() {
		return Birthday;
	}
	public void setBirthDate(Date birthDate) {
		this.Birthday = birthDate;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		this.Age = age;
	}
	public boolean isAlive() {
		return Alive;
	}
	public void setAlive(boolean alive) {
		this.Alive = alive;
	}
	public Date getDeathDate() {
		return Death;
	}
	public void setDeathDate(Date deathDate) {
		this.Death = deathDate;
	}
	public String getChild() {
		return Child;
	}
	public void setChild(String child) {
		this.Child = child;
	}
	public String getSpouse() {
		return Spouse;
	}
	public void setSpouse(String spouse) {
		this.Spouse = spouse;
	}
	

}
