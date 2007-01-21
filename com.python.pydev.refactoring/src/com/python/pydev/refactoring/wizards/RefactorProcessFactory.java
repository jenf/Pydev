/*
 * Created on Apr 9, 2006
 */
package com.python.pydev.refactoring.wizards;

import org.python.pydev.editor.codecompletion.revisited.visitors.AssignDefinition;
import org.python.pydev.editor.codecompletion.revisited.visitors.Definition;
import org.python.pydev.parser.jython.ast.ClassDef;
import org.python.pydev.parser.jython.ast.FunctionDef;
import org.python.pydev.parser.jython.ast.Name;
import org.python.pydev.parser.visitors.NodeUtils;

import com.python.pydev.refactoring.wizards.rename.PyRenameAnyLocalProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameAttributeProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameClassProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameFunctionProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameImportProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameLocalProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameParameterProcess;
import com.python.pydev.refactoring.wizards.rename.PyRenameSelfAttributeProcess;

public class RefactorProcessFactory {

	/**
	 * Decides which process should take care of the request.
	 */
    public static IRefactorRenameProcess getProcess(Definition definition) {
        if(definition instanceof AssignDefinition){
            AssignDefinition d = (AssignDefinition) definition;
            if(d.target.indexOf('.') != -1){
                if(d.target.startsWith("self.")){
                    //ok, it is a member and not a local
                    return new PyRenameSelfAttributeProcess(definition, d.target);
                }else{
                    return new PyRenameAttributeProcess(definition, d.target);
                }
                
            }else{
                if(definition.scope != null){
                	//classvar
                	if(definition.scope.isLastClassDef()){
                		return new PyRenameAttributeProcess(definition, d.target);
                	}
                }
                return new PyRenameLocalProcess(definition);
            }
        }
        if(definition.ast != null){
            if(definition.ast instanceof ClassDef){
                return new PyRenameClassProcess(definition);
            }
            
            if(definition.ast instanceof Name){
            	Name n = (Name) definition.ast;
            	if(n.ctx == Name.Param){
            		return new PyRenameParameterProcess(definition);
            	}
            }
            
            if(definition.ast instanceof FunctionDef){
                return new PyRenameFunctionProcess(definition);
            }
            if(NodeUtils.isImport(definition.ast)){
                //this means that we found an import and we cannot actually map that import to a definition
                //(so, it is an unresolved import)
            	return new PyRenameImportProcess(definition);
            }
        }else{
        	//the definition ast is null. This should mean that it was actually an import
        	//and pointed to some module
        	return new PyRenameImportProcess(definition);
        	
        }
        if(definition.scope != null){
        	//classvar
        	if(definition.scope.isLastClassDef()){
        		return new PyRenameAttributeProcess(definition, definition.value);
        	}
        }
        return new PyRenameLocalProcess(definition);
    }

	public static IRefactorRenameProcess getRenameAnyProcess() {
		return new PyRenameAnyLocalProcess();
	}

}
