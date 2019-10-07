package com.jaiswal.bitcointrends

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint
import com.jaiswal.bitcointrends.viewModels.GraphActivityViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphActivity : AppCompatActivity() {
    private lateinit var graph: GraphView
    private var x: Array<DataPoint?>? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: GraphActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: com.jaiswal.bitcointrends.databinding.ActivityMainLandscapeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_landscape)
        graph = binding.graph
        viewModel = obtainViewModel(GraphActivityViewModel::class.java)
        progressBar = binding.progressBar
        binding.button1.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            disableScreenClick()
            graph.removeAllSeries()
            x = null
            viewModel.getRepository().getChartData().observe(this, Observer {
                observeDataFetch(it)
            })
        }
        binding.button2.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            disableScreenClick()
            graph.removeAllSeries()
            x = null
            viewModel.getRemoteRepository().getChartData().observe(this, Observer {
                observeDataRefresh(it)
            })
        }
    }

    private fun observeDataFetch(it: List<FiveWeekChartPoint>?) {
        x = viewModel.convertCoordinatesToDataPoint(it)
        if (x != null) {
            if (!viewModel.lastRepositoryLocal) {
                viewModel.updatePrefs()
            }
            val series = LineGraphSeries(x)
            graph.addSeries(series)
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar.visibility = View.GONE
    }

    private fun observeDataRefresh(it: List<FiveWeekChartPoint>?) {
        x = viewModel.convertCoordinatesToDataPoint(it)
        if (x != null) {
            viewModel.updatePrefs()
            val series = LineGraphSeries(x)
            graph.addSeries(series)
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar.visibility = View.GONE
    }

    private fun disableScreenClick() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}