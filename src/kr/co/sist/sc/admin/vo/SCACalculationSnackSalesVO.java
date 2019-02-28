package kr.co.sist.sc.admin.vo;

/**
 * ���� ���� - ���� �Ǹ� ���� ���̺�
 * ������, ����, �� ����
 * @author owner
 */
public class SCACalculationSnackSalesVO {
	private String snack_name, total_price;
	private int quan;
	
	public SCACalculationSnackSalesVO(String snack_name, String total_price, int quan) {
		this.snack_name = snack_name;
		this.total_price = total_price;
		this.quan = quan;
	} // SCACalculationSnackSalesVO

	public String getSnack_name() {
		return snack_name;
	}

	public String getTotal_price() {
		return total_price;
	}

	public int getQuan() {
		return quan;
	}

	@Override
	public String toString() {
		return "SCACalculationSnackSalesVO [snack_name=" + snack_name + ", total_price=" + total_price + ", quan="
				+ quan + "]";
	} // toString
	
} // class
