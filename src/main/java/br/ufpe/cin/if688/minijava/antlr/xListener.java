package br.ufpe.cin.if688.minijava.antlr;

// Generated from x.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link xParser}.
 */
public interface xListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link xParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(xParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(xParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(xParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(xParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(xParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(xParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(xParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(xParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(xParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(xParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(xParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(xParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(xParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(xParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(xParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(xParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(xParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(xParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link xParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(xParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link xParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(xParser.IntegerLiteralContext ctx);
}