看了一篇写 Unit Test 的[教程](http://www.drdobbs.com/testing/unit-testing-with-python/240165163)，比较不错，写了段代码作为笔记

```
import unittest


class FooTest(unittest.TestCase):
    Foo = 1

    def setUp(self):
        '''set up operation for each test routine'''
        print 'setUp begin for %s:%s' % (self.shortDescription(), self.id())
        print 'setUp end for %s:%s' % (self.shortDescription(), self.id())

    def tearDown(self):
        '''tear down operation for each test routine'''
        print 'tearDown begin for %s:%s' % (self.shortDescription(), self.id())
        print 'tearDown end for %s:%s' % (self.shortDescription(), self.id())

    def testA(self):
        '''Test routine must prefix with 'test'.

        Test Routine A
        '''
        print 'testA'

    def testB(self):
        '''Test Routine B'''
        # Make test case fail
        self.fail('fail testB')
        # Will not execute
        print 'testB'

    @unittest.skip('skip test c ')
    def testC(self):
        '''Whole Routine C is skipped

        Test Routine C
        '''
        print 'testC'

    def testD(self):
        '''Test Routine D'''
        # Skip testD from here
        self.skipTest('skip testD')
        print 'testD'

    @unittest.skipIf(Foo >= 1, 'skip test e')
    def testE(self):
        '''Test Routine E'''
        print 'testE'

    @unittest.skipUnless(Foo > 1, 'skip test f')
    def testF(self):
    '''Test Routine F'''
        print 'testF'

testSuite = unittest.TestSuite()
testSuite.addTest(FooTest('testB'))
testSuite.addTest(unittest.makeSuite(FooTest))
# Count of test cases in suite, 7
print testSuite.countTestCases()

# Run test cases in testSuite
fooRunner = unittest.TextTestRunner()
result = fooRunner.run(testSuite)
# error tests
print result.errors
# failure tests
print result.failures
# skipped tests
print result.skipped
# all successful or not
print result.wasSuccessful()
# tests case that ran
print result.testsRun
# Run all test cases defined in TestCase subclass
unittest.main()
```
