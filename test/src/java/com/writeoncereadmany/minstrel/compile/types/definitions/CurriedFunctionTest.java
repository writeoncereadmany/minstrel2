package com.writeoncereadmany.minstrel.compile.types.definitions;

import com.writeoncereadmany.minstrel.compile.names.ScopeIndex;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;
import com.writeoncereadmany.minstrel.compile.types.concerns.FunctionType;
import com.writeoncereadmany.minstrel.compile.types.concerns.Implementation;
import com.writeoncereadmany.minstrel.compile.types.defintions.ConcreteTypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.defintions.TypeDefinition;
import com.writeoncereadmany.minstrel.compile.types.validators.FunctionRules;
import com.writeoncereadmany.minstrel.compile.types.validators.ImplementationRule;
import org.junit.Test;

import java.util.Map;

import static com.writeoncereadmany.minstrel.compile.types.concerns.EmptyStreamMatcher.emptyStream;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.entry;
import static com.writeoncereadmany.util.TypeSafeMapBuilder.mapOf;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class CurriedFunctionTest
{
    public static final ScopeIndex NUMBER = new ScopeIndex("Number", 3, 2);

    @Test
    public void canRepresentFunctionCallsOnTypesIndependentlyOfTheirDefinitionsAndResolveCorrectly()
    {
        final ScopeIndex functionTypeName = new ScopeIndex("func", 3, 3);
        final ConcreteTypeDefinition namedCurriedFunction = new ConcreteTypeDefinition(functionTypeName, "Curried");
        final TypeDefinition callTwice = namedCurriedFunction.returnType().returnType();

        final ConcreteTypeDefinition number = new ConcreteTypeDefinition(NUMBER, "Number");
        final FunctionType numberToNumber = new FunctionType(asList(number), number);
        final FunctionType curriedNumbers = new FunctionType(asList(number), numberToNumber);

        final Map<ScopeIndex, Type> namedTypes = mapOf(entry(NUMBER, new StructuralType(new Implementation(NUMBER))),
                                                       entry(functionTypeName, new StructuralType(curriedNumbers)));

        TypeEngine checker = new TypeEngine(asList(new FunctionRules(), new ImplementationRule()),
                                              namedTypes
        );

        assertThat(checker.checkCoherent(callTwice), is(emptyStream()));
        assertThat(checker.canAssign(callTwice, number), is(emptyStream()));
    }

    @Test
    public void attemptingToGetReturnTypesFromNonFunctionsWillResultInUndefinedTypes()
    {
        final ScopeIndex functionTypeName = new ScopeIndex("func", 3, 3);
        final ConcreteTypeDefinition namedFunction = new ConcreteTypeDefinition(functionTypeName, "Funcy");
        final TypeDefinition callTwice = namedFunction.returnType().returnType();

        // create something which returns a Number, so the second call yields an undefined type
        final ConcreteTypeDefinition number = new ConcreteTypeDefinition(NUMBER, "Number");
        final FunctionType numberToNumber = new FunctionType(asList(number), number);

        final Map<ScopeIndex, Type> namedTypes = mapOf(entry(NUMBER, new StructuralType(new Implementation(NUMBER))),
                                                       entry(functionTypeName, new StructuralType(numberToNumber)));

        TypeEngine checker = new TypeEngine(asList(new FunctionRules(), new ImplementationRule()),
                                              namedTypes
        );

        assertThat(checker.checkCoherent(callTwice), is(not(emptyStream())));
    }

    @Test
    public void attemptingToGetMissingMemberTypesWillResultInUndefinedTypes()
    {
        final ScopeIndex recordTypeName = new ScopeIndex("func", 3, 3);
        final ConcreteTypeDefinition namedRecord = new ConcreteTypeDefinition(recordTypeName, "Fooable");
        final TypeDefinition getMember = namedRecord.getMember("foo");

        // create a function: functions don't have members
        final ConcreteTypeDefinition number = new ConcreteTypeDefinition(NUMBER, "Number");
        final FunctionType numberToNumber = new FunctionType(asList(number), number);

        final Map<ScopeIndex, Type> namedTypes = mapOf(entry(NUMBER, new StructuralType(new Implementation(NUMBER))),
                                                       entry(recordTypeName, new StructuralType(numberToNumber)));

        TypeEngine checker = new TypeEngine(asList(new FunctionRules(), new ImplementationRule()),
                                              namedTypes
        );

        assertThat(checker.checkCoherent(getMember), is(not(emptyStream())));
    }
}
