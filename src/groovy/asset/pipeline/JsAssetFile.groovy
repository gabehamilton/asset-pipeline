/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package asset.pipeline

class JsAssetFile {
    static final String contentType = 'application/javascript'
    static extensions = ['js']
    static compiledExtension = 'js'
    static processors = []

    File file
    def baseFile
    def encoding


    JsAssetFile(file, baseFile=null) {
        this.file = file
        this.baseFile = baseFile
    }

    def processedStream(precompiler=false) {

        def fileText
        if(baseFile?.encoding || encoding) {
            fileText = file?.getText(baseFile?.encoding ? baseFile.encoding : encoding)
        } else {
            fileText = file?.text
        }

        for(processor in processors) {
            def processInstance = processor.newInstance()
            fileText = processInstance.process(fileText)
        }
        return fileText
        // Return File Stream
    }

    def directiveForLine(line) {
        line.find(/\/\/=(.*)/) { fullMatch, directive -> return directive }
    }
}
