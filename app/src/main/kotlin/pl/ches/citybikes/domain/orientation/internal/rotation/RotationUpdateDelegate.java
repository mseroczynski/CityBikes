package pl.ches.citybikes.domain.orientation.internal.rotation;

/**
 * Delegate to receive updates when rotation of device changes
 * 
 * @author Adam
 * 
 */
public interface RotationUpdateDelegate {
	/**
	 * 
	 * @param newMatrix
	 *            - 4x4 matrix
	 */
	public void onRotationUpdate(float newMatrix[]);
}
