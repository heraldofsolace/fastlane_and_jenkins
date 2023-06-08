package com.example.jest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jest.ui.theme.JestTheme
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun greeting() {
        val title="Hello Rockstar DEV!"
        composeTestRule.setContent {
            JestTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(title = title)
                }
            }
        }

        composeTestRule.onNodeWithText(title, useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithText("0",useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Increment Count",useUnmergedTree = true).assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("1",useUnmergedTree = true).assertExists()
    }
}