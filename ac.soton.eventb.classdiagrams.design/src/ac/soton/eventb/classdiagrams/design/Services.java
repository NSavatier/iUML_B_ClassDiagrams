package ac.soton.eventb.classdiagrams.design;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import ac.soton.eventb.classdiagrams.EventBSuperType;

/**
 * The services class used by VSM.
 */
public class Services {
    
	/**
	 * Adds target as a super type of self, then returns the modified list.
	 * @param self class to update
	 * @param target class to add as a superType of self
	 * @return list of self super types, after having added target as one of its super types
	 */
	public List<EventBSuperType> addSupertype(ac.soton.eventb.classdiagrams.Class self, ac.soton.eventb.classdiagrams.Class target) {
		List<EventBSuperType> currentSuperTypes = self.getSupertypes();
		currentSuperTypes.add(target);
		return currentSuperTypes;
	}
	
	
	/**
	 * Removes element from the super types of self, then returns the modified list.
	 * @param self class to update
	 * @param element class to remove from superTypes of self
	 * @return list of self super types, after having removed element from its super types
	 */
	public List<EventBSuperType> removeSupertype(ac.soton.eventb.classdiagrams.Class self, ac.soton.eventb.classdiagrams.Class element) {
		System.out.println(self);
		System.out.println(element);
		List<EventBSuperType> currentSuperTypes = self.getSupertypes();
		currentSuperTypes.remove(element);
		return currentSuperTypes;
	}
	
	public Boolean checkSuperTypeEndConnectionAllowed(ac.soton.eventb.classdiagrams.Class source, EObject target) {
		//A super type connection is allowed if the target is a Class 
		//and this class is not a subClass of source
		boolean targetIsClass = target instanceof ac.soton.eventb.classdiagrams.Class;
		
		boolean targetIsNotSubClassOfSource = false;
		if(targetIsClass) {
			targetIsNotSubClassOfSource = ! ((ac.soton.eventb.classdiagrams.Class)target).getSupertypes().contains(source);
		}
		
		boolean connectionAllowed = targetIsClass && targetIsNotSubClassOfSource;
		//TODO : dive into the code of the old editor to see the check that was used then
		return connectionAllowed;
	}
}
