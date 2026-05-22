package com.itn.smartitn.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import com.itn.smartitn.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    /* ─── Modèles API ─── */
    public static class Course {
        @SerializedName("id")         public int    id;
        @SerializedName("nom_cours")  public String nomCours;
        @SerializedName("code_cours") public String codeCours;
    }

    public static class CoursesResponse {
        @SerializedName("status") public boolean      status;
        @SerializedName("cours")  public List<Course> cours;
    }

    /* ─── Interface Retrofit (partagée avec EvaluationActivity) ─── */
    public interface ApiService {
        @GET("API/courses")
        Call<CoursesResponse> getCourses();

        @GET("API/criteres")
        Call<EvaluationActivity.CriteresResponse> getCriteres();
    }

    /* ─── Instance Retrofit statique ─── */
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://itn.ub.edu.bi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final ApiService apiService = retrofit.create(ApiService.class);

    /* ─── Adapter RecyclerView ─── */
    static class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.VH> {

        interface OnClick { void onClick(Course c); }

        private final List<Course> list;
        private final OnClick      listener;

        CourseAdapter(List<Course> list, OnClick listener) {
            this.list     = list;
            this.listener = listener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.evaluation_item_course, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH h, int pos) {
            Course c = list.get(pos);
            h.tvNom.setText(c.nomCours);
            h.tvCode.setText(c.codeCours);
            h.itemView.setOnClickListener(v -> listener.onClick(c));
        }

        @Override public int getItemCount() { return list.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvNom, tvCode;
            VH(View v) {
                super(v);
                tvNom  = v.findViewById(R.id.tv_nom_cours);
                tvCode = v.findViewById(R.id.tv_code_cours);
            }
        }
    }

    /* ─── Activity ─── */
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_main);

        recyclerView = findViewById(R.id.recycler_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCourses();
    }

    private void loadCourses() {
        apiService.getCourses().enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call,
                                   Response<CoursesResponse> r) {
                if (r.isSuccessful() && r.body() != null && r.body().status) {
                    List<Course> courses = r.body().cours;
                    recyclerView.setAdapter(new CourseAdapter(courses, course -> {
                        Intent i = new Intent(MainActivity.this,
                                EvaluationActivity.class);
                        i.putExtra("course_id",   course.id);
                        i.putExtra("course_name", course.nomCours);
                        startActivity(i);
                    }));
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.erreur_chargement,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.reseau, t.getMessage()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}