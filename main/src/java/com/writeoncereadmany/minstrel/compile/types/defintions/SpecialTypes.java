package com.writeoncereadmany.minstrel.compile.types.defintions;

import com.writeoncereadmany.minstrel.compile.types.Nothing;
import com.writeoncereadmany.minstrel.compile.types.StructuralType;
import com.writeoncereadmany.minstrel.compile.types.Type;
import com.writeoncereadmany.minstrel.compile.types.TypeEngine;

public enum SpecialTypes implements TypeDefinition
{
    ANYTHING
    {
        @Override
        public Type getType(TypeEngine checker)
        {
            // Anything is a very simple type: one which offers no guarantees whatsoever
            return new StructuralType();
        }

        @Override
        public TypeDefinition returnType()
        {
            return new UndefinedType("Cannot call Anything: not a function");
        }

        @Override
        public TypeDefinition getMember(String member)
        {
            return new UndefinedType("Cannot call Anything: not a method");
        }
    },
    NOTHING
    {
        @Override
        public Type getType(TypeEngine checker)
        {
            // Nothing, however, is an unpopulated type which has to be handled slightly differently
            return Nothing.INSTANCE;
        }

        @Override
        public TypeDefinition returnType()
        {
            return this;
        }

        @Override
        public TypeDefinition getMember(String member)
        {
            return this;
        }
    };
}
