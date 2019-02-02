package top.jacktu;
import java.io.Serializable;
/**
 * ��װ������Ϣ��ʵ����
 * @author jacktu
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 1816515354545010461L;
	/** ���� */
	private int level;
	/** ���� */
	private String name;
	public Person() {
		super();
	}
	public Person(int level, String name) {
		super();
		this.level = level;
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
