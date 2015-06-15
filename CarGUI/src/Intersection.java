public class Intersection {
	IntersectionAttributes leftAttribute, rightAttribute, upAttribute,
			downAttribute;
	Intersection left, right, up, down;
	Vector2D point;

	public Intersection(Vector2D point) {
		this.point = point;
		computeAttributes();
	}

	public Intersection(Vector2D point, Intersection left, Intersection right,
			Intersection up, Intersection down) {
		this.point = point;
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		computeAttributes();
	}

	public void setNeighbours(Intersection left, Intersection right,
			Intersection up, Intersection down) {
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}

	private void computeAttributes() {
		// TODO compute attributes
	}
}
