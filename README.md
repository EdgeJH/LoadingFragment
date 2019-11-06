[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/EdgeJH/LoadingFragment.svg)](https://jitpack.io/#EdgeJH/LoadingFragment)


# LoadingFragment
Easy Loading Fragment

우리는 서버로 부터 많은 데이터 통신을 합니다. 그리고 그것을 프래그먼트에 리사이클러뷰를 이용하거나 각각의 자신만의 멋진 UI로 데이터를 보여줍니다. 하지만 항상 서버로 부터 데이터를 받을때 로딩이 걸리는 시간에 UI를 어떻게 처리할지 상당히 귀찮은 작업을 생각해야합니다. 이 라이브러리는 그런 불편한 코드를 쉽게 하도록 하는 부모 프래그먼트 입니다. 원하는곳에 상속 받아 사용하면 되겠습니다.

### Example

![example gif](example.gif)

## Download

``` gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
  
dependencies {
	implementation 'com.github.EdgeJH:LoadingFragment:1.1.0'
}

```

### Usage

```java

class ExampleFragment : LoadingFragment() {

    private val handler =Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setView(view as ViewGroup)
        /*Default layout_loading_fail
        * this Layout contains refresh button and OnRefreshClickListener
        * If you custom this Layout use this method
        * setFailView(R.layout.layout_loading_fail)
        * */
        setProgressColor(R.color.colorAccent)
        loadingBtn.setOnClickListener {
            startLoading()
            loadingFail()
        }
        setOnRefreshClickListener(object : OnRefreshClickListener {
            override fun onRefreshClick() {
                startLoading()
                loadingFinish()
            }
        })
    }

    private fun loadingFinish(){
        handler.postDelayed({
            finishLoading()
        },2000)
    }

    private fun loadingFail(){
        handler.postDelayed({
            failLoading()
        },2000)
    }
}

```

### Method:

* `startLoading()`
* `finishLoading()`
* `failLoading()`
* `setProgressColor(R.color.colorAccent)`
* `setFailView(R.layout.layout_loading_fail)`
* `setView(view as ViewGroup)`
* `setOnRefreshClickListener(object : OnRefreshClickListener {})`
        

License
--------
```
Copyright 2017 EdgeJH


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```



