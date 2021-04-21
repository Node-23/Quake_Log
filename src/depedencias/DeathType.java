package depedencias;

public class DeathType {
    private int id;
	private String name;
	private int total;

	public DeathType(int id, String name) {
		this.id = id;
		this.name = name;
		this.total = 1;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
