package android.view.ext;

/**
 * Interface for providing degrees between satellites. 
 * 
 * @author Siyamed SINIR
 *
 */
public interface IDegreeProvider {
	public float[] getDegrees(int count, float totalDegrees);
	public void setItemDegree(float startDegree, float itemDegree);
}
