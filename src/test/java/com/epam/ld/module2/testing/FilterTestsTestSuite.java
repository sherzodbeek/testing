package com.epam.ld.module2.testing;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.epam.ld.module2.testing.template")
@IncludeTags("filterTest")
class FilterTestsTestSuite {
}
