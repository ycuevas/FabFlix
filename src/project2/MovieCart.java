package project2;


public class MovieCart extends Movie{
	private int quantity;
	
	public MovieCart() {
		quantity = 1;
	}
	
	public MovieCart(int newQuantity)
	{
		quantity = newQuantity;
	}
	
	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}
	public int getQuantity() {
		return quantity;
	}
}
