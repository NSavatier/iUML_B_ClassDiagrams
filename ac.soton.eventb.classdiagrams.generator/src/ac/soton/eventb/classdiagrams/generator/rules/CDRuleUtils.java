package ac.soton.eventb.classdiagrams.generator.rules;

import java.util.ArrayList;
import java.util.List;

import org.eventb.emf.core.CorePackage;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.EventBNamedCommentedComponentElement;
import org.eventb.emf.core.context.Context;
import org.eventb.emf.core.machine.Machine;

/**
 * Some utility methods to help with the Class Diagram Rules
 * 
 * 
 */

public class CDRuleUtils {

	/**
	 *  
	 *  starting from the given root component, finds the best container component for something that refers to the given element
	 *  if no sub-component has the element in scope, returns the root itself.
	 *  
	 * @param root
	 * @param elements
	 * @return
	 */
	public static EventBNamedCommentedComponentElement getTargetContainer(EventBNamedCommentedComponentElement root, EventBElement element) {
		List<EventBNamedCommentedComponentElement> components = new ArrayList<EventBNamedCommentedComponentElement>();
		components.add((EventBNamedCommentedComponentElement) element.getContaining(CorePackage.Literals.EVENT_BNAMED_COMMENTED_COMPONENT_ELEMENT));
		EventBNamedCommentedComponentElement target = findTopContainer(components, root);
		if (target==null) target = root;
		return target;
	}
	
	/**
	 * 
	 *  starting from the given root component, finds the best container component for something that refers to the given set of elements
	 *  if no sub-component has all elements in scope, returns the root itself.
	 *  
	 * @param root
	 * @param elements
	 * @return
	 */
	public static EventBNamedCommentedComponentElement getTargetContainer(EventBNamedCommentedComponentElement root, List<EventBElement> elements) {
		List<EventBNamedCommentedComponentElement> components = new ArrayList<EventBNamedCommentedComponentElement>();
		for (EventBElement element : elements) {
			components.add((EventBNamedCommentedComponentElement) element.getContaining(CorePackage.Literals.EVENT_BNAMED_COMMENTED_COMPONENT_ELEMENT));
		}
		EventBNamedCommentedComponentElement target = findTopContainer(components, root);
		if (target==null) target = root;
		
		return target;
	}

	/**
	 * search the in-scope components from 'root' to find one that is contained in the given list of containers
	 * 
	 * @param components
	 * @param root
	 * @return
	 */
	public static EventBNamedCommentedComponentElement findTopContainer(List<EventBNamedCommentedComponentElement> components, EventBNamedCommentedComponentElement root) {
		
		if  (components.contains(root)) {
			//check all others are in scope
			for (EventBNamedCommentedComponentElement c:components ) {
				if (!inScope(c,root)) return null;
			}
			return root;
		}
		
		EventBNamedCommentedComponentElement found;
		if (root instanceof Machine) {
			for (Context ctx : ((Machine)root).getSees()) {
				found  = findTopContainer(components, ctx);
				if (found!=null) return found;
			}
		}
		
		if (root instanceof Context) {
			for (Context ctx : ((Context)root).getExtends()) {
				found = findTopContainer(components, ctx);
				if (found!=null) return found;
			}
		}
		
		return null;
	}

	/**
	 * check whether the given component, c, is in-scope from the given root component
	 * 
	 * @param c
	 * @param candidate
	 * @return
	 */
	public static boolean inScope(EventBNamedCommentedComponentElement c, EventBNamedCommentedComponentElement root) {
		if (c==root) return true;

		if (root instanceof Machine) {
			for (Context ctx : ((Machine)root).getSees()) {
				if (inScope(c, ctx)) return true;
			}
		}

		if (root instanceof Context) {
			for (Context ctx : ((Context)root).getExtends()) {
				if (inScope(c, ctx)) return true;
			}
		}
		
		return false;
	}
	

	
}
