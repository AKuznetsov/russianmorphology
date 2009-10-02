/**
 * Copyright 2009 Alexander Kuznetsov 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.morpholgy.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class IgnoredFormReader {
    private String fileName;
    private String fileEncoding = "windows-1251";

    public IgnoredFormReader(String fileName) {
        this.fileName = fileName;
    }

    public IgnoredFormReader(String fileName, String fileEncoding) {
        this.fileName = fileName;
        this.fileEncoding = fileEncoding;
    }

    public Set<String> getIngnoredFroms() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), fileEncoding));
        String s = bufferedReader.readLine();
        HashSet<String> result = new HashSet<String>();
        while (s != null) {
            if (!s.startsWith("//")) {
                result.add(s.trim().split(" ")[0]);
            }
            s = bufferedReader.readLine();
        }
        return result;
    }
}
