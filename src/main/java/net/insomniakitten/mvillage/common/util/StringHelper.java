package net.insomniakitten.mvillage.common.util;

/*
 *  Copyright 2017 InsomniaKitten
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.google.common.base.Converter;
import net.insomniakitten.mvillage.ModelVillage;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_UNDERSCORE;

public class StringHelper {

    private static final Converter<String, String> LU_TO_LC = LOWER_UNDERSCORE.converterTo(LOWER_CAMEL);

    public static String formatToLangKey(String string) {
        return ModelVillage.MOD_ID + "." + LU_TO_LC.convert(string);
    }

}
