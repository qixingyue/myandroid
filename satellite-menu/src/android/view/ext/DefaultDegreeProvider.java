package android.view.ext;

import android.util.Log;

/**
 * Default provider for degrees between satellites. For number of satellites up to 3
 * tries to keep satellites centered in the given total degrees. For number equal and
 * bigger than four, distirbutes evenly using min and max degrees. 
 *  
 * @author Siyamed SINIR
 *
 */
public class DefaultDegreeProvider implements IDegreeProvider {
	
	private float startDegree = 0;
	private float itemDegree = 0;

	
	public void setItemDegree(float startDegree, float itemDegree){
		this.startDegree = startDegree;
		this.itemDegree = itemDegree;
	}
	
	public float[] getDegrees(int count, float totalDegrees){
		
		if(count < 1)
        {
            return new float[]{};
        }

        float[] result = null;
        int tmpCount = count - 1;
        
        result = new float[count];
        float delta ;
        Log.i("TAG", itemDegree + "");
        if(itemDegree == 0) {
        	delta= totalDegrees / tmpCount;
        } else {
        	delta = itemDegree;
        }
        
        for(int index=0; index<count; index++){        	
            int tmpIndex = index;
            result[index] = startDegree + tmpIndex * delta;
        }
        
        return result;
	}


}
